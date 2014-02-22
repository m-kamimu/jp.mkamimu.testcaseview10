package jp.mkamimu.testcaseview10;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AssertStatement;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BreakStatement;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.ContinueStatement;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EmptyStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.LabeledStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.SynchronizedStatement;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclarationStatement;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

public class ASTMethodDeclarationLineVisitorImpl extends ASTVisitor {
	List<String> methodDeclarationList = new ArrayList<String>();
	private boolean allassertFlag = false;

	private boolean assertFlag = false;
	private int maxint = 0;
	private int tmpint = 0;
	
	public int getMaxint() {
		return maxint;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.TypeDeclaration)
	 */
	@Override
	public boolean visit(TypeDeclaration node) {
		// TODO Auto-generated method stub
		//methodDeclarationList.add(node.getName().toString());
		return super.visit(node);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.MethodDeclaration)
	 */
	@Override
	public boolean visit(MethodDeclaration node) {
		// TODO Auto-generated method stub
		methodDeclarationList.add(node.getName().toString());
		return super.visit(node);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#endVisit(org.eclipse.jdt.core.dom.MethodInvocation)
	 */
	@Override
	public void endVisit(MethodInvocation node) {
		// TODO Auto-generated method stub
		if (assertFlag) {
			if (maxint < tmpint) {
				maxint = tmpint;
			}
			tmpint = 0;
			setAllassertFlag(true);
			assertFlag = false;
		}
		super.endVisit(node);
	}
	

	public List<String> getMethodDeclarationList() {
		return methodDeclarationList;
	}
	
	private void visit(Statement node) {
		if (node.toString().contains("assert")) {
			assertFlag = true;
		}
		tmpint++;
		return;
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.TypeDeclaration)
	 */
	@Override
	public boolean visit(AssertStatement node) {
		if (node instanceof Statement) {
			visit((Statement) node);
		}
		return super.visit(node);
	}
	
	public boolean visit(Block node) {
		if (node instanceof Statement) {
			visit((Statement) node);
		}
		return super.visit(node);
	}

	public boolean visit(BreakStatement node) {
		if (node instanceof Statement) {
			visit((Statement) node);
		}
		return super.visit(node);
	}

	public boolean visit(ConstructorInvocation node) {
		if (node instanceof Statement) {
			visit((Statement) node);
		}
		return super.visit(node);
	}
	
	public boolean visit(ContinueStatement node) {
		if (node instanceof Statement) {
			visit((Statement) node);
		}
		return super.visit(node);
	}

	public boolean visit(DoStatement node) {
		if (node instanceof Statement) {
			visit((Statement) node);
		}
		return super.visit(node);
	}

	public boolean visit(EmptyStatement node) {
		if (node instanceof Statement) {
			visit((Statement) node);
		}
		return super.visit(node);
	}

	public boolean visit(ExpressionStatement node) {
		if (node instanceof Statement) {
			visit((Statement) node);
		}
		return super.visit(node);
	}

	public boolean visit(ForStatement node) {
		if (node instanceof Statement) {
			visit((Statement) node);
		}
		return super.visit(node);
	}
	
	public boolean visit(IfStatement node) {
		if (node instanceof Statement) {
			visit((Statement) node);
		}
		return super.visit(node);
	}
	
	public boolean visit(LabeledStatement node) {
		if (node instanceof Statement) {
			visit((Statement) node);
		}
		return super.visit(node);
	}

	public boolean visit(ReturnStatement node) {
		if (node instanceof Statement) {
			visit((Statement) node);
		}
		return super.visit(node);
	}
	
	public boolean visit(SuperConstructorInvocation node) {
		if (node instanceof Statement) {
			visit((Statement) node);
		}
		return super.visit(node);
	}
	
	public boolean visit(SwitchCase node) {
		if (node instanceof Statement) {
			visit((Statement) node);
		}
		return super.visit(node);
	}

	public boolean visit(SwitchStatement node) {
		if (node instanceof Statement) {
			visit((Statement) node);
		}
		return super.visit(node);
	}

	public boolean visit(SynchronizedStatement node) {
		if (node instanceof Statement) {
			visit((Statement) node);
		}
		return super.visit(node);
	}

	public boolean visit(ThrowStatement node) {
		if (node instanceof Statement) {
			visit((Statement) node);
		}
		return super.visit(node);
	}

	public boolean visit(TryStatement node) {
		if (node instanceof Statement) {
			visit((Statement) node);
		}
		return super.visit(node);
	}

	public boolean visit(TypeDeclarationStatement node) {
		if (node instanceof Statement) {
			visit((Statement) node);
		}
		return super.visit(node);
	}
	
	public boolean visit(VariableDeclarationStatement node) {
		if (node instanceof Statement) {
			visit((Statement) node);
		}
		return super.visit(node);
	}
	
	public boolean visit(WhileStatement node) {
		if (node instanceof Statement) {
			visit((Statement) node);
		}
		return super.visit(node);
	}

	public boolean visit(EnhancedForStatement node) {
		if (node instanceof Statement) {
			visit((Statement) node);
		}
		return super.visit(node);
	}

	public boolean isAllassertFlag() {
		return allassertFlag;
	}

	public void setAllassertFlag(boolean allassertFlag) {
		this.allassertFlag = allassertFlag;
	}

}
