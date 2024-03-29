package jp.mkamimu.testcaseview10;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
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
import org.eclipse.jdt.core.dom.Statement;
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
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.part.ViewPart;

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
						showText(getOneMethodICompilationUnitInfo((ICompilationUnit)project));
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
		StyledText textControl = textviewer.getTextWidget();
		Font font = new Font(textControl.getDisplay(), new FontData("Courier",10, SWT.SIMPLE)); 
		textControl.setFont(font);
		
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
	
	private int assertnum = 0;
	private int maxnum = 0;
	
	private void setassertNum(int num) {
		if (assertnum < num) {
			assertnum = num;
		}
	}
	
	private void setmaxNum(int num) {
		if (maxnum < num) {
			maxnum = num;
		}
	}
	
	public int getassertnum() {
		return assertnum;
	}
	public int getmaxnum() {
		return maxnum;
	}
	public void resetmaxnum() {
		maxnum = 0;
	}
	public void resetassertnum() {
		assertnum = 0;
	}
	
	public void getOneMethodICompilationUnitLineInfo(ICompilationUnit unit) 
			throws JavaModelException {

		// assert statement search
		ASTParser parser = ASTParser.newParser(AST.JLS4);
		parser.setSource(unit);
		
		CompilationUnit unitp = (CompilationUnit)parser.createAST(new NullProgressMonitor());
		
		//System.out.println("Start");
		ASTMethodDeclarationLineVisitorImpl astmvis = new ASTMethodDeclarationLineVisitorImpl();
		unitp.accept(astmvis);
		if (astmvis.isAllassertFlag()) {
			this.assertnum = 1;
		} else {
			this.assertnum = 0;
		}
		this.maxnum = astmvis.getMaxint();
		
	}
	
	
	
	
	public String getOneMethodICompilationUnitInfo(ICompilationUnit unit) 
			throws JavaModelException {
		//getAllMethodICompliationUnitInfo(unit.getJavaProject());
		StringBuffer str = new StringBuffer();	

		// assert statement search
		ASTParser parser = ASTParser.newParser(AST.JLS4);
		parser.setSource(unit);
		
		CompilationUnit unitp = (CompilationUnit)parser.createAST(new NullProgressMonitor());
		
		System.out.println("Start");
		ASTMethodDeclarationVisitorImpl astmvis = new ASTMethodDeclarationVisitorImpl();
		unitp.accept(astmvis);
		List<String> methodlist = astmvis.getMethodDeclarationList();

		HashMap<Statement, Integer> stlistall = new HashMap<Statement, Integer>();
		HashMap<Statement, String> stliststrall = new HashMap<Statement, String>();
		
		for (int j = 0; j < methodlist.size(); j++) {
			ASTVisitorImpl astvis = new ASTVisitorImpl();
			String methodname = methodlist.get(j);
			str.append(methodname + "\n");
			//System.out.println(methodname + "\n");
			
			astvis.setCurrentMethod(methodname);
			astvis.setHashMap(stlistall);
			astvis.setHashMapstr(stliststrall);
			// for assert
			astvis.setSearchmode(false);
			unitp.accept(astvis);
			astvis.setSearchmode(true);
			unitp.accept(astvis);
			
			// first arg
			while(astvis.needAnalysis()) {
				//for (int i = 0; i < 10; i++) {
				astvis.countup();
				astvis.setSearchmode(false);
				unitp.accept(astvis);
				astvis.setSearchmode(true);
				unitp.accept(astvis);
			}
			
			
			String tmpsb = astvis.printassertarglist();
			/*
			if (tmpsb.length() > 0) {
				setassertNum(1);
			}
			*/
			astvis.printarglist();
			//astvis.printwholelist();
	
			//str.add(astvis.printassertwholelist());
			//str.add("\n");
			//str.add(astvis.printwholelist());
			stlistall = astvis.getHashMap();
			stliststrall = astvis.getHashMapstr();

			if (stlistall != null && stliststrall != null) {
				ASTStatementVisitorImpl aststvis = new ASTStatementVisitorImpl(stlistall, stliststrall);
				aststvis.setCurrentMethod(methodname);
				//aststvis.setLevel(2);
				str.append("--------------half level-----------------\n");

				aststvis.setLevelbyRate(0.5);
				unitp.accept(aststvis);
				str.append(aststvis.getString());
				
				str.append("--------------all level-----------------\n");
				aststvis = new ASTStatementVisitorImpl(stlistall, stliststrall);
				aststvis.setCurrentMethod(methodname);
				aststvis.setLevelbyRate(1.0);
				unitp.accept(aststvis);
				str.append(aststvis.getString());
				//str.append("--------------assert to max from last-------------\n");
				
				//str.append(createLastAssert(aststvis.getStrlist(), aststvis.getLevel()));
				str.append("--------------assert to first from last-------------\n");

				str.append(createLastAssert2(aststvis.getStrlist(), aststvis.getLevel()));

				str.append("--------------assert to first from last longest-------------\n");

				str.append(createLongAssert(aststvis.getStrlist(), aststvis.getLevel()));
				
				//str.append("--------------assert once from last-------------\n");
				//str.append(createLastAssert3(aststvis.getStrlist(), aststvis.getLevel()));

				
				
				ASTVisitorAssignImpl astvisas = new ASTVisitorAssignImpl();
				unitp.accept(astvisas);
				str.append("--------------assign info-------------\n");
				str.append(astvisas.printTestInformation(methodname));

				str.append("--------------assign no new info-------------\n");
				str.append(astvisas.printNoNewTestInformation(methodname));

				str.append("--------------assign new info-------------\n");
				str.append(astvisas.printNewTestInformation(methodname));

				aststvis.clearString();

				//str.append("--------------all level-----------------\n");

				aststvis = new ASTStatementVisitorImpl(stlistall, stliststrall);
				aststvis.setCurrentMethod(methodname);
				aststvis.setLevelbyRate(1.0);
				unitp.accept(aststvis);
				//str.append(aststvis.getString());
				
				str.append("--------------assign + slice? info-------------\n");

				str.append(createLastAssert4(aststvis.getStrlist(), aststvis.getLevel()));

				str.append("--------------assign no  + slice? info-------------\n");
				str.append(createLastAssert5(aststvis.getStrlist(), aststvis.getLevel()));

				str.append("--------------assign + slice? all info-------------\n");

				str.append(createLastAssert4all(aststvis.getStrlist(), aststvis.getLevel()));

				str.append("--------------assign no  + slice? all info-------------\n");
				str.append(createLastAssert5all(aststvis.getStrlist(), aststvis.getLevel()));

				
				aststvis.clearString();
				

				
			}
		}
		
		//for(Integer keys : linelistall.keySet()) {
			//System.out.println(keys +":"+ linelistall.get(keys));
		//}
		
		
		/*
		try {
			BufferedReader reader = new BufferedReader(new StringReader(unit.getSource()));
			int l = 1;
			String line;
			while((line = reader.readLine()) != null) {
				Integer linenum = stlistall.get(l);
				//String linetoken = lineliststrall.get(l);
				
				if (linenum == null) {
					System.out.println(l + "::								"+line);
					str.append(l + "::								"+line+"\n");
				} else {
					System.out.println(l+":" + linelistall.get(l) + ":" + lineliststrall.get(l) + ":						"+line);
					str.append(l+":" + linelistall.get(l) + ":" + lineliststrall.get(l) + ":						"+line+"\n");
					setmaxNum(linelistall.get(l));
				}
				l++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
			
		
		return str.toString();
	}
	
	private String currentProject = null;

	/**
	 * @param currentProject the currentProject to set
	 */
	public void setCurrentProject(String currentProject) {
		this.currentProject = currentProject;
	}
	
	private String createLastAssert(List<StrData> strdlist , int level) {
		StringBuffer sb = new StringBuffer();
		int current = 0;
		String currentstr = null;
		
		boolean writeflag = false;
		for(int i = strdlist.size() - 1; i >= 0; i--) {
			int l = strdlist.get(i).getL();
			String line = strdlist.get(i).getLine();
			String lstr = strdlist.get(i).getLstr();
			if (line.contains("assert")) {
				writeflag = true;
				sb.insert(0,String.format("%2d:%-20s:\t%s", l, lstr, line));
				current = l;
				currentstr = lstr;
			} else if (writeflag) {
				if (current < l || !currentstr.equals(lstr)) {
					sb.insert(0,String.format("%2d:%-20s:\t%s", l, lstr, line));
					current = l;
					currentstr = lstr;
				}
			}
			if (level > 0 && current >= level) {
				writeflag = false;
			}
		}
		return sb.toString();
	}

	
	private String createLastAssert2(List<StrData> strdlist , int level) {
		StringBuffer sb = new StringBuffer();
		int current = 0;
		String currentstr = null;
		
		boolean writeflag = false;
		for(int i = strdlist.size() - 1; i >= 0; i--) {
			int l = strdlist.get(i).getL();
			String line = strdlist.get(i).getLine();
			String lstr = strdlist.get(i).getLstr();
			if (line.contains("assert")) {
				writeflag = true;
				sb.insert(0,String.format("%2d:%-20s:\t%s", l, lstr, line));
				current = l;
				currentstr = lstr;
			} else if (writeflag) {
				if (current < l || !currentstr.equals(lstr)) {
					sb.insert(0,String.format("%2d:%-20s:\t%s", l, lstr, line));
					current = l;
					currentstr = lstr;
				}
			}
			/*if (level > 0 && current >= level) {
				//writeflag = false;
			}*/
		}
		return sb.toString();
	}

	private String createLongAssert(List<StrData> strdlist , int level) {
		StringBuffer sb = new StringBuffer();
		int current = 0;
		String currentstr = null;
		String currentLongline = null;
		
		boolean writeflag = false;
		for(int i = strdlist.size() - 1; i >= 0; i--) {
			int l = strdlist.get(i).getL();
			String line = strdlist.get(i).getLine();
			String lstr = strdlist.get(i).getLstr();
			if (line.contains("assert")) {
				writeflag = true;
				sb.insert(0,String.format("%2d:%-20s:\t%s", l, lstr, line));
				current = l;
				currentstr = lstr;
				currentLongline = line;
			} else if (writeflag) {
				if (current < l || !currentstr.equals(lstr)) {
					if (!currentLongline.contains("assert")) {
						sb.insert(0,String.format("%2d:%-20s:\t%s", current, currentstr, currentLongline));
					}
					current = l;
					currentstr = lstr;
					currentLongline = line;
				} else if (current == l || currentstr.equals(lstr)) {
					if (currentLongline.length() < line.length()) {
						current = l;
						currentstr = lstr;
						currentLongline = line;
					}
				}
			}
		}
		if (currentstr != null && currentLongline != null) {
			sb.insert(0,String.format("%2d:%-20s:\t%s", current, currentstr, currentLongline));
		}
		return sb.toString();
	}
	

	private String createLastAssert3(List<StrData> strdlist , int level) {
		StringBuffer sb = new StringBuffer();
		List<Integer> current = new ArrayList<Integer>(); 
		List<String> currentstr = new ArrayList<String>();
		
		boolean writeflag = false;
		for(int i = strdlist.size() - 1; i >= 0; i--) {
			int l = strdlist.get(i).getL();
			String line = strdlist.get(i).getLine();
			String lstr = strdlist.get(i).getLstr();
			if (!writeflag && line.contains("assert")) {
				writeflag = true;
				sb.insert(0,String.format("%2d:%-20s:\t%s", l, lstr, line));
				current.add(l);
				currentstr.add(lstr);
			} else if (writeflag) {
				if (!current.contains(l) || !currentstr.contains(lstr)) {
					sb.insert(0,String.format("%2d:%-20s:\t%s", l, lstr, line));
					current.add(l);
					currentstr.add(lstr);
				}
			}
			/*if (level > 0 && current >= level) {
				//writeflag = false;
			}*/
		}
		return sb.toString();
	}
	

	private String createLastAssert4(List<StrData> strdlist , int level) {
		StringBuffer sb = new StringBuffer();
		List<Integer> current = new ArrayList<Integer>(); 
		List<String> currentstr = new ArrayList<String>();
		
		boolean writeflag = false;
		for(int i = strdlist.size() - 1; i >= 0; i--) {
			int l = strdlist.get(i).getL();
			String line = strdlist.get(i).getLine();
			String lstr = strdlist.get(i).getLstr();
			if (!writeflag && line.contains("assert")) {
				writeflag = true;
				sb.insert(0,String.format("%2d:%-20s:\t%s", l, lstr, line));
				current.add(l);
				currentstr.add(lstr);
			} else if (writeflag && line.contains("=")) {
				if (!current.contains(l) || !currentstr.contains(lstr)) {
					sb.insert(0,String.format("%2d:%-20s:\t%s", l, lstr, line));
					current.add(l);
					currentstr.add(lstr);
				}
			}
			/*if (level > 0 && current >= level) {
				//writeflag = false;
			}*/
		}
		return sb.toString();
	}


	private String createLastAssert5(List<StrData> strdlist , int level) {
		StringBuffer sb = new StringBuffer();
		List<Integer> current = new ArrayList<Integer>(); 
		List<String> currentstr = new ArrayList<String>();
		
		boolean writeflag = false;
		for(int i = strdlist.size() - 1; i >= 0; i--) {
			int l = strdlist.get(i).getL();
			String line = strdlist.get(i).getLine();
			String lstr = strdlist.get(i).getLstr();
			if (!writeflag && line.contains("assert")) {
				writeflag = true;
				sb.insert(0,String.format("%2d:%-20s:\t%s", l, lstr, line));
				current.add(l);
				currentstr.add(lstr);
			} else if (writeflag && !line.contains("=")) {
				if (!current.contains(l) || !currentstr.contains(lstr)) {
					sb.insert(0,String.format("%2d:%-20s:\t%s", l, lstr, line));
					current.add(l);
					currentstr.add(lstr);
				}
			}
			/*if (level > 0 && current >= level) {
				//writeflag = false;
			}*/
		}
		return sb.toString();
	}
	
	private String createLastAssert4all(List<StrData> strdlist , int level) {
		StringBuffer sb = new StringBuffer();
		List<Integer> current = new ArrayList<Integer>(); 
		List<String> currentstr = new ArrayList<String>();
		
		boolean writeflag = false;
		for(int i = strdlist.size() - 1; i >= 0; i--) {
			int l = strdlist.get(i).getL();
			String line = strdlist.get(i).getLine();
			String lstr = strdlist.get(i).getLstr();
			if (!writeflag && line.contains("assert")) {
				writeflag = true;
				sb.insert(0,String.format("%2d:%-20s:\t%s", l, lstr, line));
				current.add(l);
				currentstr.add(lstr);
			} else if (writeflag && line.contains("=")) {
				//if (!current.contains(l) || !currentstr.contains(lstr)) {
					sb.insert(0,String.format("%2d:%-20s:\t%s", l, lstr, line));
					current.add(l);
					currentstr.add(lstr);
				//}
			}
			/*if (level > 0 && current >= level) {
				//writeflag = false;
			}*/
		}
		return sb.toString();
	}


	private String createLastAssert5all(List<StrData> strdlist , int level) {
		StringBuffer sb = new StringBuffer();
		List<Integer> current = new ArrayList<Integer>(); 
		List<String> currentstr = new ArrayList<String>();
		
		boolean writeflag = false;
		for(int i = strdlist.size() - 1; i >= 0; i--) {
			int l = strdlist.get(i).getL();
			String line = strdlist.get(i).getLine();
			String lstr = strdlist.get(i).getLstr();
			if (!writeflag && line.contains("assert")) {
				writeflag = true;
				sb.insert(0,String.format("%2d:%-20s:\t%s", l, lstr, line));
				current.add(l);
				currentstr.add(lstr);
			} else if (writeflag && !line.contains("=")) {
				//if (!current.contains(l) || !currentstr.contains(lstr)) {
					sb.insert(0,String.format("%2d:%-20s:\t%s", l, lstr, line));
					current.add(l);
					currentstr.add(lstr);
				//}
			}
			/*if (level > 0 && current >= level) {
				//writeflag = false;
			}*/
		}
		return sb.toString();
	}
	

	
}
