package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import dao.MemberDao;

public class SignUpView extends JFrame implements ActionListener {
	
	private JLabel signLabel;
	private JLabel[] textName = new JLabel[4];
	private JTextField[] inPut_d = new JTextField[4];
	private JButton idCheck;
	private JButton signBtn;
	
	private Container c;
	
	public SignUpView() {
		
		super("회원가입");
		
		c = getContentPane();
		c.setLayout(null);
		
		// 뷰 제목
		signLabel = new JLabel("회원가입 페이지");
		signLabel.setBounds(110, 20, 120, 20);
		signLabel.setHorizontalAlignment(JLabel.CENTER);
		c.add(signLabel);
		// 텍스트필드 이름
		String[] str1 = {"ID :", "PassWord :", "이름 :", "E-mail :"};
		for (int i = 0; i < str1.length; i++) {
			textName[i] = new JLabel(str1[i]);
			textName[i].setBounds(15, 58+(i*50), 80, 30);
			textName[i].setHorizontalAlignment(JLabel.CENTER);
			c.add(textName[i]);
		}
		// 텍스트필드
		for (int i = 0; i < inPut_d.length; i++) {
			inPut_d[i] = new JTextField(40);
			inPut_d[i].setBounds(90, 65+(i*50), 160, 20);
			c.add(inPut_d[i]);
		}
		// ID중복확인
		idCheck = new JButton("ID");
		idCheck.setBounds(260, 65, 60, 20);
		idCheck.addActionListener(this);
		c.add(idCheck);
		
		// 회원가입 버튼
		signBtn = new JButton("회원가입");
		signBtn.setBounds(50, 260, 230, 35);
		signBtn.addActionListener(this);
		c.add(signBtn);
		
		
		c.setBackground(new Color(135, 206, 255));
		setBounds(650, 150, 350, 350);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		JButton btn = (JButton)e.getSource();
		String btnTitle = btn.getLabel();
		// ID확인 버튼
		if(btnTitle.equals("ID")) {
			
			// id 확인
			MemberDao dao = MemberDao.getInstance();
			String id = inPut_d[0].getText().trim();		
			// 빈 칸 확인
			if(id.equals("")) {
				JOptionPane.showMessageDialog(null, "ID를 입력해주세요.");
				return;
			}
			// id 중복 확인
			boolean b = dao.getId(id);		
			if(b == true) {
				JOptionPane.showMessageDialog(null, "사용할 수 없는 ID입니다.");
				inPut_d[0].setText("");
			}
			else {
				JOptionPane.showMessageDialog(null, "이 ID는 사용하실 수 있습니다.");
			}
		}
		// 회원가입 버튼
		else if(btnTitle.equals("회원가입")) {
			// 빈 칸 확인
			for (int i = 0; i < inPut_d.length; i++) {
				if(inPut_d[i].getText().equals("")){
					JOptionPane.showMessageDialog(null, "입력하지 않은 정보가 있습니다.");
					return;
				}
			}
			// 입력한 텍스트 필드의 문자열 저장		
			String id = inPut_d[0].getText();
			String pwd = inPut_d[1].getText();
			String name = inPut_d[2].getText();
			String email = inPut_d[3].getText();		
			
			MemberDao dao = MemberDao.getInstance();
			// 회원가입 내용이 성공적으로 추가됐으면 true , 실패하면 false
			boolean insert_check = dao.addMember(id, pwd, name, email);
			// 회원가입 성공
			if(insert_check) {
			JOptionPane.showMessageDialog(null, "회원가입되셨습니다.");
			
			new LoginView();
			this.dispose();
			}
			// 회원가입 실패
			else JOptionPane.showMessageDialog(null, "회원가입에 실패하셨습니다.");
					
		}	
	}
}
