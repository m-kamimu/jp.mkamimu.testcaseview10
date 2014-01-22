package ca.ubc.cs.mkamimu.testcaseview10;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IMarkSelection;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.part.ViewPart;

import ca.ubc.cs.mkamimu.testcaseview10.marker.DescriptionData;
import ca.ubc.cs.mkamimu.testcaseview10.marker.DescriptionDataForWriting;
import ca.ubc.cs.mkamimu.testcaseview10.marker.DescriptionDataHolder;
import ca.ubc.cs.mkamimu.testcaseview10.marker.SampleMarker;
import ca.ubc.cs.mkamimu.testcaseview10.marker.SampleMarker10;
import ca.ubc.cs.mkamimu.testcaseview10.marker.SampleMarker2;
import ca.ubc.cs.mkamimu.testcaseview10.marker.SampleMarker20;

/**
 * This view simply mirrors the current selection in the workbench window.
 * It works for both, element and text selection.
 */
public class SelectionView extends ViewPart {

	private PageBook pagebook;
	private TableViewer tableviewer;
	private TextViewer textviewer;
	
	// the listener we register with the selection service 
	private ISelectionListener listener = new ISelectionListener() {
		public void selectionChanged(IWorkbenchPart sourcepart, ISelection selection) {
			// we ignore our own selections
			if (sourcepart != SelectionView.this) {
			    showSelection(sourcepart, selection);
			}
		}
	};
	
	/**
	 * Shows the given selection in this view.
	 */
	private void showSelection(IWorkbenchPart sourcepart, ISelection selection) {
		setContentDescription(sourcepart.getTitle() + " (" + selection.getClass().getName() + ")");
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ss = (IStructuredSelection) selection;
			Object firstElement = ss.getFirstElement();
			
			if (firstElement instanceof IAdaptable) {
				IAdaptable adaptable = (IAdaptable) firstElement;
				Object project = null;
				boolean showitemcalled = false;
				try {
					project = adaptable.getAdapter(IProject.class);
					if (project instanceof IProject) {
						showItems(printProjectInfo((IProject)project));
						showitemcalled = true;
					}
					
					project = adaptable.getAdapter(IPackageFragment.class);				
					if (project instanceof IPackageFragment) {
						//showItems(printPackageInfos(((IPackageFragment) project).getJavaProject()));
						showText(getICompilationUnitInfo((IPackageFragment)project));
						showitemcalled = true;
					}
					
					project = adaptable.getAdapter(ICompilationUnit.class);								
					if (project instanceof ICompilationUnit) {
						showText(getOneMethodICompilationUnitInfo((ICompilationUnit)project, false));
						showitemcalled = true;
					}
					//project = adaptable.getAdapter(IClass)
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				if (!showitemcalled) {
					showItems(ss.toArray());					
				}
			} else {
				showItems(ss.toArray());
			}
		}
		
		
		if (selection instanceof ITextSelection) {
			/*
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				    .getActivePage();
			IEditorInput editorInput = page.getActiveEditor().getEditorInput();
			IFile file = (IFile) editorInput.getAdapter(IFile.class);
			ICompilationUnit unit = JavaCore.createCompilationUnitFrom(file);
			try {
				showText(getOneMethodICompilationUnitInfo(unit, false));
			} catch (JavaModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			/*
			ITextSelection ts  = (ITextSelection) selection;
			showText(ts.getText());
			*/
		}
		
		if (selection instanceof IMarkSelection) {
			IMarkSelection ms = (IMarkSelection) selection;
			try {
			    showText(ms.getDocument().get(ms.getOffset(), ms.getLength()));
			} catch (BadLocationException ble) { }
		}
	}
	
	private void showItems(Object[] items) {
		tableviewer.setInput(items);
		pagebook.showPage(tableviewer.getControl());
	}
	
	private void showText(String text) {
		textviewer.setDocument(new Document(text));
		pagebook.showPage(textviewer.getControl());
	}
	
	public void createPartControl(Composite parent) {
		// the PageBook allows simple switching between two viewers
		pagebook = new PageBook(parent, SWT.NONE);
		
		tableviewer = new TableViewer(pagebook, SWT.NONE);
		tableviewer.setLabelProvider(new WorkbenchLabelProvider());
		tableviewer.setContentProvider(new ArrayContentProvider());
		
		// we're cooperative and also provide our selection
		// at least for the tableviewer
		getSite().setSelectionProvider(tableviewer);
		
		textviewer = new TextViewer(pagebook, SWT.H_SCROLL | SWT.V_SCROLL);
		//textviewer.setEditable(false);
		textviewer.setEditable(true);
		
		getSite().getWorkbenchWindow().getSelectionService().addSelectionListener(listener);
	}

	public void setFocus() {
		pagebook.setFocus();
	}

	public void dispose() {
		// important: We need do unregister our listener when the view is disposed
		getSite().getWorkbenchWindow().getSelectionService().removeSelectionListener(listener);
		super.dispose();
	}
	
	private Object[] printProjectInfo(IProject project) throws CoreException,
		JavaModelException {
		List<Object> str = new ArrayList<Object>();	
		str.add(project); 

		// Check if we have a Java project
		if (project.isOpen() && project.isNatureEnabled("org.eclipse.jdt.core.javanature")) {
			IJavaProject javaProject = JavaCore.create(project);
			return printPackageInfos(javaProject);
		} else {
			return str.toArray();
		}
	}

	private Object[] printPackageInfos(IJavaProject javaProject)
		throws JavaModelException {
		List<Object> str = new ArrayList<Object>();
		str.add(javaProject);
		
		IPackageFragment[] packages = javaProject.getPackageFragments();
		for (IPackageFragment mypackage : packages) {
			// Package fragments include all packages in the
			// classpath
			// We will only look at the package from the source
			// folder
			// K_BINARY would include also included JARS, e.g.
			// rt.jar
			//System.out.println("--------------------------------------------------------------------");
			if (mypackage.getKind() == IPackageFragmentRoot.K_SOURCE) {
				str.add(mypackage);
				//getICompilationUnitInfo(mypackage);
			}
		}

		//getMapInfo(this.opList, this.asList);
		return str.toArray();
	}
	
	private String getICompilationUnitInfo(IPackageFragment mypackage) 
			throws JavaModelException {
		List<String> str = new ArrayList<String>();	

		StringBuffer compstr = new StringBuffer();
		for(ICompilationUnit mycompunit: mypackage.getCompilationUnits() ) {
			compstr.append(mycompunit.getElementName());
			compstr.append("\n");
		}
		str.add(mypackage.getElementName());
		str.add(compstr.toString());
		str.add("\n");

		return str.toString();
	}
	
	private int[] a1 = {0,0,0,0};
	
	public int largest() {
		int current, max, count;
		
		current = a1[0];
		max = current;
		count = -1;
		for(int i = 0; i < a1.length; i++) {
			current = a1[i];
			if(current > max) {
				max = current;
				count = i;
			}
		}
		return count;
	}
	
	
	public String getOneMethodICompilationUnitInfo(ICompilationUnit unit, boolean loglikelihood) 
			throws JavaModelException {
		//getAllMethodICompliationUnitInfo(unit.getJavaProject());
		List<String> str = new ArrayList<String>();	

		// assert statement search
		ASTParser parser = ASTParser.newParser(AST.JLS4);
		parser.setSource(unit);
		
		CompilationUnit unitp = (CompilationUnit)parser.createAST(new NullProgressMonitor());
		
		System.out.println("Start");
		ASTVisitorImpl astvis = new ASTVisitorImpl(unitp, this.globalTestInformation);
		// for assert
		unitp.accept(astvis);
		
		// first arg
		astvis.countup();
		unitp.accept(astvis);
		// second arg
		astvis.countup();
		unitp.accept(astvis);
		// third arg
		astvis.countup();
		unitp.accept(astvis);

		// fourth arg
		astvis.countup();
		unitp.accept(astvis);

		// fifth arg
		astvis.countup();
		unitp.accept(astvis);

		
		astvis.printassertarglist();
		astvis.printarglist();

		astvis.printassertwholelist();
		astvis.printwholelist();

		
		/*
		IResource irs = ((ICompilationUnit) unit).getCorrespondingResource();
		a1[0] = SampleMarker.createMarker(irs, astvis.localTestInformation, globalTestInformation);
		a1[1] = SampleMarker10.createMarker(irs, astvis.localTestInformation, globalTestInformation);
		a1[2] = SampleMarker2.createMarker(irs, astvis.localTestInformation, globalTestInformation);
		a1[3] = SampleMarker20.createMarker(irs, astvis.localTestInformation, globalTestInformation);
		
		//str.add(SampleMarker.getLastm());  //assertinfo?
		//str.add(SampleMarker2.getLastm()); // normal method info?
		
		DescriptionDataHolder assertstr = SampleMarker.getLastm2();
		DescriptionDataHolder invostr = SampleMarker2.getLastm2();
		
		List<String> testname = assertstr.getTestName(astvis.localTestInformation.getClassName());
		List<String> testname2 = invostr.getTestName(astvis.localTestInformation.getClassName());
		
		for(int i = 0; i < testname.size(); i++) {
			String testnameelm = testname.get(i);
			if (!testname2.contains(testnameelm)) {
				testname2.add(testnameelm);
			}
		}
		
		str.add(astvis.localTestInformation.getClassName() + "\n");
		
		
		for(int i = 0; i < testname2.size(); i++) {
			DescriptionDataForWriting description1 = new DescriptionDataForWriting();
			DescriptionDataForWriting description2 = new DescriptionDataForWriting();
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(testname2.get(i) + "\t:\t");
			for(int j = 0; j < 2; j++) {
				description1.add(assertstr.getNextItem(testname2.get(i)));
				description2.add(invostr.getNextItem(testname2.get(i)));
				//strbuf.append(testname2.get(i) + "\t:\t");
			}
			
			List<DescriptionData> deslist1 = description1.getDescriptionInOrder();
			List<DescriptionData> deslist2 = description2.getDescriptionInOrder();

			int d1count = 0;
			int d2count = 0;
			while (d1count < deslist1.size() || d2count < deslist2.size()) {
				DescriptionData d1 = null;
				DescriptionData d2 = null;
				if (d1count < deslist1.size()) {
					d1 = deslist1.get(d1count);
				}
				if (d2count < deslist2.size()) {
					d2 = deslist2.get(d2count);
				}
				
				if (d1 != null && d2 != null) {
					if (d1.getStartcolumn() < d2.getStartcolumn()) {
						//strbuf.append(", " + d1.getDescription());
						strbuf.append(", " + d1.getDescriptionWithReplace());
						d1count++;
					} else {
						//strbuf.append(", " + d2.getDescription());
						strbuf.append(", " + d2.getDescriptionWithReplace());
						d2count++;
					}
				} else if (d1 == null) {
					strbuf.append(", " + d2.getDescriptionWithReplace());
					d2count++;
				} else if (d2 == null) {
					strbuf.append(", " + d1.getDescriptionWithReplace());
					d1count++;
				}
			}
			str.add(strbuf.toString() + "\n");
			strbuf.delete(0, strbuf.length());
		}
		//str.add(SampleMarker2.getLastm2()); // normal method info?
		*/
		return str.toString();
	}
	
	private String currentProject = null;
	private TestInformation globalTestInformation = new TestInformation();

	private TestInformation getAllMethodICompliationUnitInfo(IJavaProject javaProject)
			throws JavaModelException {
		if (currentProject != null && currentProject.equals(javaProject.getElementName())) {
			return this.globalTestInformation;
		} else {
			currentProject = javaProject.getElementName();
			this.globalTestInformation.setLock(false);
			this.globalTestInformation.getMethodDList().clear();
			this.globalTestInformation.getMethodIList().clear();
			this.globalTestInformation.getMethodAList().clear();
			this.globalTestInformation.getMethodOnlyIList().clear();
			this.globalTestInformation.getMethodOnlyAList().clear();
			this.globalTestInformation.getMethodAArgList().clear();
			this.globalTestInformation.getMethodIArgList().clear();
			//this.globalTestInformation.getSourceFiles().clear();
		}
		
		IPackageFragment[] packages = javaProject.getPackageFragments();
		for (IPackageFragment mypackage : packages) {
			// Package fragments include all packages in the
			// classpath
			// We will only look at the package from the source
			// folder
			// K_BINARY would include also included JARS, e.g.
			// rt.jar
			//System.out.println("--------------------------------------------------------------------");
			if (mypackage.getKind() == IPackageFragmentRoot.K_SOURCE) {
				for (ICompilationUnit unit : mypackage.getCompilationUnits()) {
					// assert statement search
					ASTParser parser = ASTParser.newParser(AST.JLS4);
					parser.setSource(unit);
					CompilationUnit unitp = (CompilationUnit)parser.createAST(new NullProgressMonitor());
					ASTVisitorImpl astvis = new ASTVisitorImpl(unitp, this.globalTestInformation);
					unitp.accept(astvis);
				}
			}
		}
		this.globalTestInformation.setLock(true);
		return this.globalTestInformation;
	}

	/**
	 * @param currentProject the currentProject to set
	 */
	public void setCurrentProject(String currentProject) {
		this.currentProject = currentProject;
	}
	
}
