package ca.ubc.cs.mkamimu.testcaseview10;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.StructuralPropertyDescriptor;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class ASTVisitorImpl extends ASTVisitor {
	
	CompilationUnit cu;
	//TestInformation localTestInformation = new TestInformation();	
	//TestInformation globalTestInformation = null;
	
	private int countflag = 0;
	
	public void countup() {
		countflag++;
	}
	
	ASTVisitorImpl(CompilationUnit cu, TestInformation testinfo) {
		
		this.cu = cu;
		/*
		this.globalTestInformation = testinfo;
		if (!this.globalTestInformation.isLock()) {
			//this.globalTestInformation.setSourceFile(cu.toString());
		}
		localTestInformation.setClassName(cu.getJavaElement().getElementName());
		*/
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
		/*
		if(!globalTestInformation.isLock()) {
			globalTestInformation.getMethodDList().add(node.getName().toString());
		}
		localTestInformation.getMethodDList().add(node.getName().toString());
		 */
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
		/*
		if(!globalTestInformation.isLock()) {
			globalTestInformation.getMethodDList().add(node.getName().toString());
		}
		localTestInformation.getMethodDList().add(node.getName().toString());
		*/
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
	
	List<List<Integer>> linelist = new ArrayList();
	
	List<List<String>> arglist = new ArrayList();
	List<String> assertarglist = new ArrayList();
	
	List<List<String>> wholelist = new ArrayList();
	List<String> wholeassertarglist = new ArrayList();
	
	
	public String printassertarglist() {
		StringBuffer strbuf = new StringBuffer();
		for(int i = 0; i < assertarglist.size(); i++) {
			System.out.println("assertarglist: " + assertarglist.get(i).toString());
			strbuf.append("assertarglist: " + assertarglist.get(i).toString());
			strbuf.append("\n");
		}
		return strbuf.toString();
	}

	public String printarglist() {
		StringBuffer strbuf = new StringBuffer();
		for(int i = 0; i < arglist.size(); i++) {
			for (int j = 0; j < arglist.get(i).size(); j++) {
				System.out.println("arglist: " + i + ":" + arglist.get(i).get(j).toString());
				strbuf.append("arglist: " + i + ":" + arglist.get(i).get(j).toString());
				strbuf.append("\n");
			}
		}
		return strbuf.toString();
	}

	
	public String printassertwholelist() {
		StringBuffer strbuf = new StringBuffer();
		for(int i = 0; i < wholeassertarglist.size(); i++) {
			System.out.println("assertwholelist: " + wholeassertarglist.get(i).toString());
			strbuf.append("assertwholelist: " + wholeassertarglist.get(i).toString());
			strbuf.append("\n");
		}
		return strbuf.toString();
	}

	public String printwholelist() {
		StringBuffer strbuf = new StringBuffer();
		for(int i = 0; i < wholelist.size(); i++) {
			for (int j = 0; j < wholelist.get(i).size(); j++) {
				System.out.println("wholelist: " + i + ":" + wholelist.get(i).get(j).toString());
				strbuf.append("wholelist: " + i + ":" + wholelist.get(i).get(j).toString());
				strbuf.append("\n");
			}
			//System.out.println("wholelist: " + wholelist.get(i).toString());
		}
		return strbuf.toString();
	}
	
	private int sntmpflag = 0;
	
	public boolean visit(SimpleName node) {
		boolean tmpflag = false; 
		System.out.println("Simple:" + node.toString());
		System.out.println("SimpleParent:" + node.getParent().toString());
		
		
		if (countflag > 0) {
			if (countflag == 1) {
				//for(int i = 0; i < node.arguments().size(); i++) {
					if (assertarglist.contains(node.toString())) {
						tmpflag = true;
					}
				//}
				if (node.getParent() != null 
						&& assertarglist.contains(node.getParent().toString())) {
					tmpflag = true;
				}
				
			} else if (arglist.size() > 0 && arglist.size() > countflag - 2) {
				//for(int i = 0; i < node.arguments().size(); i++) {
					if (arglist.get(countflag - 2).contains(node.toString())) {
						tmpflag = true;
					}
				//}
				if (node.getParent() != null 
						&& arglist.get(countflag - 2).contains(node.getParent().toString())) {
					tmpflag = true;
				}
				if (linelist.get(countflag - 2).contains(cu.getLineNumber(node.getStartPosition()))) {
					tmpflag = true;
				}
				
				for(int j = 2; j <= countflag; j++) {
					if (wholelist.get(j - 2).contains(node.getParent().toString().replaceAll("\n",""))) {
						tmpflag = false;
					}
				}
				
			}
			
			if (tmpflag == true) {
				sntmpflag++;
			}

	
			if (sntmpflag > 0) {
				// test 
			// test 
			//System.out.println("cothernodeinfo:" + node.toString());
			//System.out.println("cotherarg:" + node.arguments().toString());
			//System.out.println("cothername: " + node.getName());
			//System.out.println("cothernodep:" + node.getExpression());

			List<Integer> linelistcount = new ArrayList();
			List<String> arglistcount = new ArrayList();
			List<String> wholelistcount = new ArrayList();

			//for(int i = 0; i < node.arguments().size(); i++) {
				if (!node.toString().equals("null")) {
					arglistcount.add(node.toString());
					linelistcount.add(cu.getLineNumber(node.getStartPosition()));
				}
				//arglistcount.add(node.arguments().get(i).toString());
				//arglistcount.add(node.getExpression().toString());			
			//}
			//wholelistcount.add(node.toString());
			wholelistcount.add(node.getParent().toString().replaceAll("\n",""));
			
			if (arglist.size() > countflag - 1) {
				List<String> tmparglistcount = arglist.get(countflag - 1);
				tmparglistcount.addAll(arglistcount);
				arglist.set(countflag - 1, tmparglistcount);
				
				List<String> tmpwholelistcount = wholelist.get(countflag - 1);
				tmpwholelistcount.addAll(wholelistcount);
				wholelist.set(countflag - 1, tmpwholelistcount);
				
				List<Integer> tmplinelistcount = linelist.get(countflag - 1);
				tmplinelistcount.addAll(linelistcount);
				linelist.set(countflag - 1, tmplinelistcount);
				
			} else {
				if (!arglist.contains(arglistcount)) {
					arglist.add(arglistcount);
					wholelist.add(wholelistcount);
					linelist.add(linelistcount);
				}
			}
		}
		}
		
		
		return super.visit(node);
	}
	
	public void endVisit(SimpleName node) {
		if (sntmpflag > 0) {
			sntmpflag--;
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
			
			for(int i = 0; i < node.arguments().size(); i++) {
				if (!node.arguments().get(i).toString().equals("null")) {
					assertarglist.add(node.arguments().get(i).toString());
				}
			}
			if (node.getExpression() != null) {
				assertarglist.add(node.getExpression().toString());
			}

			wholeassertarglist.add(node.toString());
			
			//System.out.println("assertname: " + node.getName());
			System.out.println("assertnodep:" + node.getExpression());
			
			assertFlag = true;
		} else if (!(assertFlag || node.getName().toString().startsWith("assert"))) {
			//methodnamelist.add(node.getName().toString());
			
			/*boolean tmpflag = false;
			if (countflag > 0) {
				if (countflag == 1) {
					for(int i = 0; i < node.arguments().size(); i++) {
						if (assertarglist.contains(node.arguments().get(i).toString())) {
							tmpflag = true;
						}
					}
					if (node.getExpression() != null 
							&& assertarglist.contains(node.getExpression().toString())) {
						tmpflag = true;
					}
					
				} else if (arglist.size() > 0 && arglist.size() > countflag - 2) {
					for(int i = 0; i < node.arguments().size(); i++) {
						if (arglist.get(countflag - 2).contains(node.arguments().get(i).toString())) {
							tmpflag = true;
						}
					}
					if (node.getExpression() != null 
							&& arglist.get(countflag - 2).contains(node.getExpression().toString())) {
						tmpflag = true;
					}
					
					for(int j = 2; j <= countflag; j++) {
						if (wholelist.get(j - 2).contains(node.getParent().toString().replaceAll("\n", ""))) {
							tmpflag = false;
						}
					}

				}
				
		
			if (tmpflag) {
				// test 
				//System.out.println("othernodeinfo:" + node.toString());
				//System.out.println("otherarg:" + node.arguments().toString());
				//System.out.println("othername: " + node.getName());
				//System.out.println("othernodep:" + node.getExpression());
				
				List<String> arglistcount = new ArrayList();
				List<String> wholelistcount = new ArrayList();
				for(int i = 0; i < node.arguments().size(); i++) {
					if (!node.arguments().get(i).toString().equals("null")) {
						arglistcount.add(node.arguments().get(i).toString());
					}
				}
				if (node.getExpression() != null) {
					arglistcount.add(node.getExpression().toString());
				}

				//wholelistcount.add(node.toString());
				wholelistcount.add(node.getParent().toString().replaceAll("\n", ""));
				
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
			}*/
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
		/*
		boolean tmpflag = false;
		if (countflag > 0) {
			if (countflag == 1) {
				for(int i = 0; i < node.arguments().size(); i++) {
					if (assertarglist.contains(node.arguments().get(i).toString())) {
						tmpflag = true;
					}
				}
				if (node.getExpression() != null 
						&& assertarglist.contains(node.getExpression().toString())) {
					tmpflag = true;
				}
				
			} else if (arglist.size() > 0 && arglist.size() > countflag - 2) {
				for(int i = 0; i < node.arguments().size(); i++) {
					if (arglist.get(countflag - 2).contains(node.arguments().get(i).toString())) {
						tmpflag = true;
					}
				}
				if (node.getExpression() != null 
						&& arglist.get(countflag - 2).contains(node.getExpression().toString())) {
					tmpflag = true;
				}
				
				for(int j = 2; j <= countflag; j++) {
					if (wholelist.get(j - 2).contains(node.getParent().toString().replaceAll("\n",""))) {
						tmpflag = false;
					}
				}

			}

	
			if (tmpflag) {
				// test 
			// test 
			//System.out.println("cothernodeinfo:" + node.toString());
			//System.out.println("cotherarg:" + node.arguments().toString());
			//System.out.println("cothername: " + node.getName());
			//System.out.println("cothernodep:" + node.getExpression());
	
			List<String> arglistcount = new ArrayList();
			List<String> wholelistcount = new ArrayList();

			for(int i = 0; i < node.arguments().size(); i++) {
				if (!node.arguments().get(i).toString().equals("null")) {
					arglistcount.add(node.arguments().get(i).toString());
				}
				//arglistcount.add(node.arguments().get(i).toString());
				//arglistcount.add(node.getExpression().toString());			
			}
			//wholelistcount.add(node.toString());
			wholelistcount.add(node.getParent().toString().replaceAll("\n",""));
			
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
		*/
		return super.visit(node);
	}
	
	
	private boolean variableDeclarationFragFlag = false;
	private boolean isAssignFlag = true;
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.VariableDeclarationFragment)
	 */
	public boolean visit(VariableDeclarationFragment node) {
		/*
		boolean tmpflag = false;
		if (countflag > 0) {
			if (countflag == 1) {
				//for(int i = 0; i < node.arguments().size(); i++) {
					if (assertarglist.contains(node.getName().toString())) {
						tmpflag = true;
					}
					if (node.getInitializer() != null && assertarglist.contains(node.getInitializer().toString())) {
						tmpflag = true;
					}
					
				//}
				/*if (node.getExpression() != null 
						&& assertarglist.contains(node.getExpression().toString())) {
					tmpflag = true;
				}*/
			/*	
			} else if (arglist.size() > 0 && arglist.size() > countflag - 2) {
				if (arglist.contains(node.getName().toString())) {
					tmpflag = true;
				}
				if (node.getInitializer() != null && arglist.contains(node.getInitializer().toString())) {
					tmpflag = true;
				}

				/*for(int i = 0; i < node.arguments().size(); i++) {
					if (arglist.get(countflag - 2).contains(node.arguments().get(i).toString())) {
						tmpflag = true;
					}
				}
				/*if (node.getExpression() != null 
						&& arglist.get(countflag - 2).contains(node.getExpression().toString())) {
					tmpflag = true;
				}*/
				/*
				for(int j = 2; j <= countflag && j < wholelist.size() + 1; j++) {
					if (wholelist.get(j - 2).contains(node.getParent().toString().replaceAll("\n",""))) {
						tmpflag = false;
					}
				}

			}

	
			if (tmpflag) {
				// test 
			// test 
			//System.out.println("cothernodeinfo:" + node.toString());
			//System.out.println("cotherarg:" + node.arguments().toString());
			//System.out.println("cothername: " + node.getName());
			//System.out.println("cothernodep:" + node.getExpression());
	
			List<String> arglistcount = new ArrayList();
			List<String> wholelistcount = new ArrayList();

			if (!node.getName().toString().equals("null")) {
				arglistcount.add(node.getName().toString());
			}
			if (node.getInitializer() != null && !node.getInitializer().toString().equals("null")) {
				arglistcount.add(node.getInitializer().toString());
			}

			/*for(int i = 0; i < node.arguments().size(); i++) {
				if (!node.arguments().get(i).toString().equals("null")) {
					arglistcount.add(node.arguments().get(i).toString());
				}
				//arglistcount.add(node.arguments().get(i).toString());
				//arglistcount.add(node.getExpression().toString());			
			}*/
			//wholelistcount.add(node.toString());
			/*wholelistcount.add(node.getParent().toString().replaceAll("\n", ""));
			
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
			 */
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
		/*
		boolean tmpflag = false;
		if (countflag > 0) {
			if (countflag == 1) {
				//for(int i = 0; i < node.arguments().size(); i++) {
					if (assertarglist.contains(node.getLeftHandSide().toString())) {
						tmpflag = true;
					}
					if (assertarglist.contains(node.getRightHandSide().toString())) {
						tmpflag = true;
					}
					
				//}
				/*if (node.getExpression() != null 
						&& assertarglist.contains(node.getExpression().toString())) {
					tmpflag = true;
				}*/
			/*	
			} else if (arglist.size() > 0 && arglist.size() > countflag - 2) {
				if (arglist.contains(node.getLeftHandSide().toString())) {
					tmpflag = true;
				}
				if (arglist.contains(node.getRightHandSide().toString())) {
					tmpflag = true;
				}

				/*for(int i = 0; i < node.arguments().size(); i++) {
					if (arglist.get(countflag - 2).contains(node.arguments().get(i).toString())) {
						tmpflag = true;
					}
				}
				/*if (node.getExpression() != null 
						&& arglist.get(countflag - 2).contains(node.getExpression().toString())) {
					tmpflag = true;
				}*/
				/*
				for(int j = 2; j <= countflag && j <= wholelist.size() + 1; j++) {
					if (wholelist.get(j - 2).contains(node.getParent().toString().replaceAll("\n", ""))) {
						tmpflag = false;
					}
				}

			}

	
			if (tmpflag) {
				// test 
			// test 
			//System.out.println("cothernodeinfo:" + node.toString());
			//System.out.println("cotherarg:" + node.arguments().toString());
			//System.out.println("cothername: " + node.getName());
			//System.out.println("cothernodep:" + node.getExpression());
	
			List<String> arglistcount = new ArrayList();
			List<String> wholelistcount = new ArrayList();

			if (!node.getLeftHandSide().toString().equals("null")) {
				arglistcount.add(node.getLeftHandSide().toString());
			}
			if (!node.getRightHandSide().toString().equals("null")) {
				arglistcount.add(node.getRightHandSide().toString());
			}

			/*for(int i = 0; i < node.arguments().size(); i++) {
				if (!node.arguments().get(i).toString().equals("null")) {
					arglistcount.add(node.arguments().get(i).toString());
				}
				//arglistcount.add(node.arguments().get(i).toString());
				//arglistcount.add(node.getExpression().toString());			
			}*/
			//wholelistcount.add(node.toString());
			/*wholelistcount.add(node.getParent().toString().replaceAll("\n", ""));
			
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

		
		// test 
		System.out.println("asothernodeinfo:" + node.toString());
		//System.out.println("dotherarg:" + node.arguments().toString());
		//System.out.println("dothername: " + node.getName());
		System.out.println("asothernodel:" + node.getLeftHandSide());
		System.out.println("asothernoder:" + node.getRightHandSide());

		
		isAssignFlag = true;

		if (node.getLeftHandSide().toString() != null && node.getRightHandSide().toString() != null) {
			AssignInformation asninfo = new AssignInformation();
			asninfo.setTestinfo(this.currentMethod.peek());
			asninfo.setAssignInfo(
					node.getLeftHandSide().toString(), node.getRightHandSide().toString(),
					node.getStartPosition(), node.getLength()
					);
			//this.localTestInformation.addAssigninfo(asninfo);
			
			/*
			System.out.print(this.currentMethod.peek() + ":");
			System.out.print(node.getLeftHandSide().toString());
			System.out.print(":leftright:");
			System.out.println(node.getRightHandSide().toString());
			*/
		/*}*/
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
