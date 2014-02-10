package jp.mkamimu.testcaseview10;

import java.util.HashMap;
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
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.SynchronizedStatement;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.TypeDeclarationStatement;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

public class ASTStatementVisitorImpl extends ASTVisitor {
	private HashMap<Statement, Integer> stlist = null;
	private HashMap<Statement, String> stliststr = null;
	private StringBuffer str = new StringBuffer();
	
	
	public ASTStatementVisitorImpl(HashMap<Statement, Integer> stlist, HashMap<Statement, String> stliststr) {
		// TODO Auto-generated constructor stub
		this.stlist = stlist;
		this.stliststr = stliststr;
	}
	
	public String getString() {
		return str.toString();
	}
	
	
	private void visit(Statement node) {
		//System.out.println(node.toString());
		if (node != null && this.stlist != null) {
			int l = this.stlist.get(node);
			String lstr = this.stliststr.get(node);
			String line = node.toString();
			//System.out.println(l + "::								"+line);
			//str.append(l + "::								"+line+"\n");
			System.out.printf("%2d:%-20s:\t%s", l, lstr, line);
			//System.out.print(l + ":" + lstr + "			:						"+line);
			str.append(String.format("%2d:%-20s:\t%s", l, lstr, line));
			//str.append(l + ":" + lstr + "			:						"+line);
		}
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
	
	/*
	public boolean visit(Block node) {
		if (node instanceof Statement) {
			visit((Statement) node);
		}
		return super.visit(node);
	}
	*/

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

	/*
	public boolean visit(TryStatement node) {
		if (node instanceof Statement) {
			visit((Statement) node);
		}
		return super.visit(node);
	}
	*/

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
	
}