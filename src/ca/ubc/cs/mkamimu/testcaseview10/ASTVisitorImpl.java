package ca.ubc.cs.mkamimu.testcaseview10;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class ASTVisitorImpl extends ASTVisitor {
	
	CompilationUnit cu;
	TestInformation localTestInformation = new TestInformation();	
	TestInformation globalTestInformation = null;
	
	private int countflag = 0;
	
	public void countup() {
		countflag++;
	}
	
	
	ASTVisitorImpl(CompilationUnit cu, TestInformation testinfo) {
		this.cu = cu;
		this.globalTestInformation = testinfo;
		if (!this.globalTestInformation.isLock()) {
			//this.globalTestInformation.setSourceFile(cu.toString());
		}
		localTestInformation.setClassName(cu.getJavaElement().getElementName());
		//System.out.println(cu.getJavaElement().getElementName());
	}

	Stack<String> currentMethod = new Stack<String>();
	
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.TypeDeclaration)
	 */
	@Override
	public boolean visit(TypeDeclaration node) {
		// TODO Auto-generated method stub
		currentMethod.push(node.getName().toString());
		if(!globalTestInformation.isLock()) {
			globalTestInformation.getMethodDList().add(node.getName().toString());
		}
		localTestInformation.getMethodDList().add(node.getName().toString());

		return super.visit(node);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#endVisit(org.eclipse.jdt.core.dom.TypeDeclaration)
	 */
	@Override
	public void endVisit(TypeDeclaration node) {
		// TODO Auto-generated method stub
		if (node.getName().equals(currentMethod.peek())) {
			currentMethod.pop();
		}

		super.endVisit(node);
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.MethodDeclaration)
	 */
	@Override
	public boolean visit(MethodDeclaration node) {
		// TODO Auto-generated method stub
		currentMethod.push(node.getName().toString());
		if(!globalTestInformation.isLock()) {
			globalTestInformation.getMethodDList().add(node.getName().toString());
		}
		localTestInformation.getMethodDList().add(node.getName().toString());
		return super.visit(node);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#endVisit(org.eclipse.jdt.core.dom.MethodDeclaration)
	 */
	@Override
	public void endVisit(MethodDeclaration node) {
		// TODO Auto-generated method stub
		if (node.getName().equals(currentMethod.peek())) {
			currentMethod.pop();
		}
		super.endVisit(node);
	}
	
	List<List<String>> arglist = new ArrayList();
	List<String> assertarglist = new ArrayList();
	
	List<List<String>> wholelist = new ArrayList();
	List<String> wholeassertarglist = new ArrayList();
	
	
	public void printassertarglist() {
		for(int i = 0; i < assertarglist.size(); i++) {
			System.out.println("assertarglist: " + assertarglist.get(i).toString());
		}		
	}

	public void printarglist() {
		for(int i = 0; i < arglist.size(); i++) {
			System.out.println("arglist: " + arglist.get(i).toString());
		}		
	}

	
	public void printassertwholelist() {
		for(int i = 0; i < wholeassertarglist.size(); i++) {
			System.out.println("assertwholelist: " + wholeassertarglist.get(i).toString());
		}		
	}

	public void printwholelist() {
		for(int i = 0; i < wholelist.size(); i++) {
			System.out.println("wholelist: " + wholelist.get(i).toString());
		}		
	}

	
	
	
	private boolean assertFlag = false;
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.MethodInvocation)
	 */
	@Override
	public boolean visit(MethodInvocation node) {
		// TODO Auto-generated method stub
		if ((assertFlag || node.getName().toString().startsWith("assert")) && countflag == 0) {
			// test 
			System.out.println("assertnodeinfo:" + node.toString());
			System.out.println("assertarg:" + node.arguments().toString());
			
			for(int i = 0; i < node.arguments().size(); i++) {
				assertarglist.add(node.arguments().get(i).toString());
			}
			wholeassertarglist.add(node.toString());
			
			System.out.println("assertname: " + node.getName());
			System.out.println("assertnodep:" + node.getExpression());
			
			/*
			if(!globalTestInformation.isLock()) {
				globalTestInformation.getMethodADList().add(currentMethod.peek());				
				globalTestInformation.getMethodAList().add(node.toString());
				globalTestInformation.getMethodAintsList().add(node.getStartPosition());
				globalTestInformation.getMethodAintlList().add(node.getLength());
				globalTestInformation.getMethodOnlyAList().add(node.getName().toString());
				globalTestInformation.getMethodAArgList().add(node.arguments().toString());
				globalTestInformation.getTryflag().add(tryflag);
				globalTestInformation.getCatchflag().add(catchflag);
				if (node.getExpression() != null) {
					globalTestInformation.getExprOnlyAList().add(node.getExpression().toString());
				} else {
					globalTestInformation.getExprOnlyAList().add("");				
				}
			}
			localTestInformation.getMethodADList().add(currentMethod.peek());				
			localTestInformation.getMethodAList().add(node.toString());
			localTestInformation.getMethodAintsList().add(node.getStartPosition());
			localTestInformation.getMethodAintlList().add(node.getLength());
			localTestInformation.getMethodOnlyAList().add(node.getName().toString());
			localTestInformation.getMethodAArgList().add(node.arguments().toString());
			localTestInformation.getTryflag().add(tryflag);
			localTestInformation.getCatchflag().add(catchflag);
			if (node.getExpression() != null) {
				localTestInformation.getExprOnlyAList().add(node.getExpression().toString());
			} else {
				localTestInformation.getExprOnlyAList().add("");				
			}

			/*
			System.out.println("localap:" + node.getParent().toString());
			System.out.println("locala:" + node.toString());
			System.out.println("localaa:" + node.arguments().toString());
			if (node.getExpression() != null) {
				System.out.println("localaex:" + node.getExpression().toString());
			} else {
				System.out.println("localaex:" + "no expression");
			}
			*/
			assertFlag = true;
		} else if (!(assertFlag || node.getName().toString().startsWith("assert"))) {
			boolean tmpflag = false;
			if (countflag > 0) {

				if (countflag == 1) {
					for(int i = 0; i < node.arguments().size(); i++) {
						if (assertarglist.contains(node.arguments().get(i).toString())) {
							tmpflag = true;
						}
					}
				} else if (arglist.size() > 0) {
					for(int i = 0; i < node.arguments().size(); i++) {
						if (arglist.get(countflag - 2).contains(node.arguments().get(i).toString())) {
							tmpflag = true;
						}
					}
				}
		
			if (tmpflag) {
				// test 
				System.out.println("othernodeinfo:" + node.toString());
				System.out.println("otherarg:" + node.arguments().toString());
				System.out.println("othername: " + node.getName());
				System.out.println("othernodep:" + node.getExpression());
				
				List<String> arglistcount = new ArrayList();
				List<String> wholelistcount = new ArrayList();
				for(int i = 0; i < node.arguments().size(); i++) {
					arglistcount.add(node.arguments().get(i).toString());
				}
				wholelistcount.add(node.toString());
				
				if (arglist.size() > countflag - 1) {
					List<String> tmparglistcount = arglist.get(countflag - 1);
					tmparglistcount.addAll(arglistcount);
					arglist.set(countflag - 1, tmparglistcount);
					
					List<String> tmpwholelistcount = wholelist.get(countflag - 1);
					tmpwholelistcount.addAll(wholelistcount);
					wholelist.set(countflag - 1, tmpwholelistcount);
					
				} else {
					if (!arglist.contains(arglistcount)) {
						arglist.add(arglistcount);
						wholelist.add(wholelistcount);
					}
				}
				
			}
			}
			/*
			if(!globalTestInformation.isLock()) {
				globalTestInformation.getMethodIDList().add(currentMethod.peek());				
				globalTestInformation.getMethodIList().add(node.toString());
				globalTestInformation.getMethodIintsList().add(node.getStartPosition());
				globalTestInformation.getMethodIintlList().add(node.getLength());
				globalTestInformation.getMethodOnlyIList().add(node.getName().toString());
				globalTestInformation.getMethodIArgList().add(node.arguments().toString());
				globalTestInformation.getTryflag().add(tryflag);
				globalTestInformation.getCatchflag().add(catchflag);
				if (node.getExpression() != null) {
					globalTestInformation.getExprOnlyIList().add(node.getExpression().toString());
				} else {
					globalTestInformation.getExprOnlyIList().add("");				
				}
			}
			localTestInformation.getMethodIDList().add(currentMethod.peek());				
			localTestInformation.getMethodIList().add(node.toString());			
			localTestInformation.getMethodIintsList().add(node.getStartPosition());
			localTestInformation.getMethodIintlList().add(node.getLength());
			localTestInformation.getMethodOnlyIList().add(node.getName().toString());
			localTestInformation.getMethodIArgList().add(node.arguments().toString());
			localTestInformation.getTryflag().add(tryflag);
			localTestInformation.getCatchflag().add(catchflag);
			if (node.getExpression() != null) {
				localTestInformation.getExprOnlyIList().add(node.getExpression().toString());
			} else {
				localTestInformation.getExprOnlyIList().add("");				
			}

			/*
			System.out.println("localip:" + node.getParent().toString());
			System.out.println("locali:" + node.toString());
			System.out.println("localia:" + node.arguments().toString());
			if (node.getExpression() != null) {
				System.out.println("localiex:" + node.getExpression().toString());
			} else {
				System.out.println("localiex:" + "no expression");
			}
			*/

		}
		
		return super.visit(node);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#endVisit(org.eclipse.jdt.core.dom.MethodInvocation)
	 */
	@Override
	public void endVisit(MethodInvocation node) {
		// TODO Auto-generated method stub
		if (assertFlag || node.getName().toString().startsWith("assert")) {
			assertFlag = false;
		}
		super.endVisit(node);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#endVisit(org.eclipse.jdt.core.dom.ClassInstanceCreation)
	 */
	@Override
	public void endVisit(ClassInstanceCreation node) {
		// TODO Auto-generated method stub
		super.endVisit(node);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.ClassInstanceCreation)
	 */
	@Override
	public boolean visit(ClassInstanceCreation node) {
		
		boolean tmpflag = false;
		if (countflag > 0) {

			if (countflag == 1) {
				for(int i = 0; i < node.arguments().size(); i++) {
					if (assertarglist.contains(node.arguments().get(i).toString())) {
						tmpflag = true;
					}
				}
			} else if (arglist.size() > 0) {
				for(int i = 0; i < node.arguments().size(); i++) {
					if (arglist.get(countflag - 2).contains(node.arguments().get(i).toString())) {
						tmpflag = true;
					}
				}
			}
	
			if (tmpflag) {
				// test 
			// test 
			System.out.println("cothernodeinfo:" + node.toString());
			System.out.println("cotherarg:" + node.arguments().toString());
			//System.out.println("cothername: " + node.getName());
			System.out.println("cothernodep:" + node.getExpression());
	
			List<String> arglistcount = new ArrayList();
			List<String> wholelistcount = new ArrayList();

			for(int i = 0; i < node.arguments().size(); i++) {
				arglistcount.add(node.arguments().get(i).toString());
				//arglistcount.add(node.getExpression().toString());			
			}
			wholelistcount.add(node.toString());
			
			if (arglist.size() > countflag - 1) {
				List<String> tmparglistcount = arglist.get(countflag - 1);
				tmparglistcount.addAll(arglistcount);
				arglist.set(countflag - 1, tmparglistcount);
				
				List<String> tmpwholelistcount = wholelist.get(countflag - 1);
				tmpwholelistcount.addAll(wholelistcount);
				wholelist.set(countflag - 1, tmpwholelistcount);
				
			} else {
				if (!arglist.contains(arglistcount)) {
					arglist.add(arglistcount);
					wholelist.add(wholelistcount);
				}
			}
		}
		}
		
		/*
		// TODO Auto-generated method stub
		if(!globalTestInformation.isLock()) {
			globalTestInformation.getMethodIDList().add(currentMethod.peek());				
			globalTestInformation.getMethodIList().add(node.toString());
			globalTestInformation.getMethodIintsList().add(node.getStartPosition());
			globalTestInformation.getMethodIintlList().add(node.getLength());
			globalTestInformation.getMethodOnlyIList().add(node.getType().toString());
			globalTestInformation.getMethodIArgList().add(node.arguments().toString());
			globalTestInformation.getTryflag().add(tryflag);
			globalTestInformation.getCatchflag().add(catchflag);
			if (node.getExpression() != null) {
				globalTestInformation.getExprOnlyIList().add(node.getExpression().toString());
			} else {
				globalTestInformation.getExprOnlyIList().add("");				
			}
		}
		localTestInformation.getMethodIDList().add(currentMethod.peek());
		localTestInformation.getMethodIList().add(node.toString());			
		localTestInformation.getMethodIintsList().add(node.getStartPosition());
		localTestInformation.getMethodIintlList().add(node.getLength());
		localTestInformation.getMethodOnlyIList().add(node.getType().toString());
		localTestInformation.getMethodIArgList().add(node.arguments().toString());
		localTestInformation.getTryflag().add(tryflag);
		localTestInformation.getCatchflag().add(catchflag);
		if (node.getExpression() != null) {
			localTestInformation.getExprOnlyIList().add(node.getExpression().toString());
		} else {
			localTestInformation.getExprOnlyIList().add("");				
		}
		*/
		return super.visit(node);
	}
	
	
	private boolean variableDeclarationFragFlag = false;
	private boolean isAssignFlag = true;
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.VariableDeclarationFragment)
	 */
	public boolean visit(VariableDeclarationFragment node) {
		// test 
		System.out.println("dothernodeinfo:" + node.toString());
		//System.out.println("dotherarg:" + node.arguments().toString());
		System.out.println("dothername: " + node.getName());
		//System.out.println("dothernodep:" + node.getExpression());

		
		
		if (node.getName() != null && node.getInitializer() != null) {
			AssignInformation asninfo = new AssignInformation();
			asninfo.setTestinfo(this.currentMethod.peek());
			asninfo.setAssignInfo(
					node.getName().toString(), node.getInitializer().toString(),
					node.getStartPosition(), node.getLength()
					);
			this.localTestInformation.addAssigninfo(asninfo);

			/*
			System.out.print(this.currentMethod.peek() + ":");
			System.out.print(node.getName().toString());
			System.out.print(":assign:");
			System.out.println(node.getInitializer().toString());
			*/
		}
		variableDeclarationFragFlag = true;
		return super.visit(node);
	}
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#endVisit(org.eclipse.jdt.core.dom.VariableDeclarationFragment)
	 */
	public void endVisit(VariableDeclarationFragment node) {
		variableDeclarationFragFlag = false;
	}
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.Assignment)
	 */
	public boolean visit(Assignment node) {
		
		// test 
		System.out.println("asothernodeinfo:" + node.toString());
		//System.out.println("dotherarg:" + node.arguments().toString());
		//System.out.println("dothername: " + node.getName());
		//System.out.println("dothernodep:" + node.getExpression());

		
		isAssignFlag = true;

		if (node.getLeftHandSide().toString() != null && node.getRightHandSide().toString() != null) {
			AssignInformation asninfo = new AssignInformation();
			asninfo.setTestinfo(this.currentMethod.peek());
			asninfo.setAssignInfo(
					node.getLeftHandSide().toString(), node.getRightHandSide().toString(),
					node.getStartPosition(), node.getLength()
					);
			this.localTestInformation.addAssigninfo(asninfo);
			
			/*
			System.out.print(this.currentMethod.peek() + ":");
			System.out.print(node.getLeftHandSide().toString());
			System.out.print(":leftright:");
			System.out.println(node.getRightHandSide().toString());
			*/
		}
		return super.visit(node);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#endVisit(org.eclipse.jdt.core.dom.Assignment)
	 */
	public void endVisit(Assignment node) {
		isAssignFlag = false;
	}
	
	private boolean tryflag = false;
	private boolean catchflag = false;
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.TryStatement)
	 */
	public boolean visit(TryStatement node) {
		tryflag = true;
		return super.visit(node);
	}
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#endVisit(org.eclipse.jdt.core.dom.TryStatement)
	 */
	public void endVisit(TryStatement node) {
		tryflag = false;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.CatchClause)
	 */
	public boolean visit(CatchClause node) {
		catchflag = true;
		return super.visit(node);
	}
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#endVisit(org.eclipse.jdt.core.dom.CatchClause)
	 */
	public void endVisit(CatchClause node) {
		catchflag = false;
	}

	/**
	 * @return
	 */
	public boolean isTryflag() {
		return tryflag;
	}

	/**
	 * @return
	 */
	public boolean isCatchflag() {
		return catchflag;
	}
	
	
}
