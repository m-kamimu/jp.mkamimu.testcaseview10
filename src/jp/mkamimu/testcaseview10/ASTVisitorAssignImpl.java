package jp.mkamimu.testcaseview10;

import java.util.List;
import java.util.Stack;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import jp.mkamimu.testcaseview10.AssignInformation;

public class ASTVisitorAssignImpl extends ASTVisitor {

	TestInformation localTestInformation = new TestInformation();
	
	public String printTestInformation(String argtestinfo) {
		StringBuffer str = new StringBuffer();
		List<AssignInformation> asn = this.localTestInformation.getAssigninfo();
		
		for(int i = 0; i < asn.size(); i++) {
			String testinfo = asn.get(i).getTestinfo();
			if (!argtestinfo.equals(testinfo)) {
				continue;
			}
			List<String> leftlist = asn.get(i).getLeftList();
			List<String> rightlist = asn.get(i).getRightList();
			for(int j = 0; j < leftlist.size(); j++) {
				/*
				str.append(testinfo);
				str.append(":");
				str.append(leftlist.get(j));
				str.append("::");
				str.append(rightlist.get(j));
				*/
				str.append(String.format("%-20s:\t%s", leftlist.get(j), rightlist.get(j)));
				str.append("\n");
			}
			//str.append("\n");
		}
		return str.toString();
	}
	
	
	public String printNoNewTestInformation(String argtestinfo) {
		StringBuffer str = new StringBuffer();
		List<AssignInformation> asn = this.localTestInformation.getAssigninfo();
		
		for(int i = 0; i < asn.size(); i++) {
			String testinfo = asn.get(i).getTestinfo();
			if (!argtestinfo.equals(testinfo)) {
				continue;
			}
			List<String> leftlist = asn.get(i).getLeftList();
			List<String> rightlist = asn.get(i).getRightList();
			for(int j = 0; j < leftlist.size(); j++) {
				/*
				str.append(testinfo);
				str.append(":");
				str.append(leftlist.get(j));
				str.append("::");
				str.append(rightlist.get(j));
				*/
				if (!rightlist.get(j).contains("new ")) {
					str.append(String.format("%-20s:\t%s", leftlist.get(j), rightlist.get(j)));
					str.append("\n");
				}
			}
			//str.append("\n");
		}
		return str.toString();
	}
	
	

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.Assignment)
	 */
	public boolean visit(Assignment node) {
		//isAssignFlag = true;

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
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.VariableDeclarationFragment)
	 */
	public boolean visit(VariableDeclarationFragment node) {
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
		//variableDeclarationFragFlag = true;
		return super.visit(node);
	}
	
	Stack<String> currentMethod = new Stack<String>();
	
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.TypeDeclaration)
	 */
	@Override
	public boolean visit(TypeDeclaration node) {
		// TODO Auto-generated method stub
		currentMethod.push(node.getName().toString());
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
	
	
}
