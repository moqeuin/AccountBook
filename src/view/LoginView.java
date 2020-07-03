package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import dao.MemberDao;

public class LoginView extends JFrame implements ActionListener {
	
	private JLabel logLabel;
	private JLabel[] idpw = new JLabel[2];
	private JTextField id_input;
	private JPasswordField pwd_input;
	private JButton[] logBtn = new JButton[2];
	
	private Container c;
	
	public LoginView() {
		
		super("로그인");
			
		c = getContentPane();
		c.setLayout(null);
		
		// 뷰 제목
		logLabel = new JLabel("로그인 페이지");
		logLabel.setHorizontalAlignment(JLabel.CENTER);
		logLabel.setBounds(95, 20, 100, 20);
		c.add(logLabel);
		// 텍스트 이름
		String[] str1 = {"ID :", "PW :"};
		for (int i = 0; i < str1.length; i++) {
			idpw[i] = new JLabel(str1[i]);
			idpw[i].setHorizontalAlignment(JLabel.CENTER);
			idpw[i].setBounds(50, 68+(i*45), 30, 10);
			c.add(idpw[i]);
		}
		// 텍스트 필드
		
		id_input = new JTextField(20);
		id_input.setBounds(90, 65, 140, 20);
		c.add(id_input);
		
		pwd_input = new JPasswordField(20);
		pwd_input.setBounds(90, 110, 140, 20);
		c.add(pwd_input);
		// 로그인, 회원가입 버튼
		String[] str2 = {"Login", "회원가입"};
		for (int i = 0; i < str2.length; i++) {
			logBtn[i] = new JButton(str2[i]);
			logBtn[i].setBounds(30+(i*120), 155, 100, 30);
			logBtn[i].addActionListener(this);
			c.add(logBtn[i]);
		}
		
		c.setBackground(Color.white);
		setBounds(670, 200, 300, 250);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object obj = e.getSource();
		if(obj == logBtn[0]) {
			
			MemberDao dao = MemberDao.getInstance();
			
			// 입력한 id와 패스워드
			String id = id_input.getText().trim();
			String pwd = pwd_input.getText().trim();
			// 빈 칸 채우기
			if(id.equals("")||pwd.equals("")) {
				JOptionPane.showMessageDialog(null, "ID나 패스워드를 입력하지 않았습니다.");
				return;
			}
			//  id 중복확인
			int check = dao.memberCheck(id, pwd);
			//  0이면 로그인, 1이면 비밀번호를 틀림, 2이면 id와 비밀번호를 틀림
			if(check == 2) {
				JOptionPane.showMessageDialog(null, "등록되지 않은 ID입니다.");
				return;
			}
			else if(check == 1) {
				JOptionPane.showMessageDialog(null, "비밀번호를 다시 입력하세요.");
				return;
			}
			else if(check == 0) {
				
				MemberDao md = MemberDao.getInstance();
				// 접속한 id를 싱글턴에 저장
				md.setId(id);
				
				new MenuView();
				this.dispose();
			}
		}
		// 회원가입 페이지 이동
		else if(obj == logBtn[1]) {
			new SignUpView();
			this.dispose();
		}
	}
}
