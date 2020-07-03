package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import dao.MemberDao;

public class AddView extends JFrame implements ActionListener {
	
	private JButton menuBtn;
	private JRadioButton radio1, radio2;
	private ButtonGroup group;
	private JLabel label_m;
	private JTextField input_m;
	private JTextArea text_in;
	private JScrollPane js;
	private JButton addBtn;
	
	private Container c;
	
	public AddView() {
		
        super("추가");
        
        c = getContentPane();
		c.setLayout(null);
		
		// 수입, 지출 버튼
		radio1 = new JRadioButton("지출");
		radio1.setBounds(210, 10, 70, 40);
		radio1.setBackground(new Color(135, 206, 255));
		radio1.setSelected(false);
		radio1.addActionListener(this);
			
		radio2 = new JRadioButton("수입");
		radio2.setBounds(110, 10, 70, 40);
		radio2.setBackground(new Color(135, 206, 255));
		radio2.setSelected(true);
		radio2.addActionListener(this);
		
		group = new ButtonGroup();
		group.add(radio1);
		group.add(radio2);
		
		c.add(radio1);
		c.add(radio2);
		
		
		// 입력 텍스트필드 이름
		label_m = new JLabel("금액 :");
		label_m.setBounds(50, 57, 80, 20);
		c.add(label_m);
		// 입력 텍스트 필드
		input_m = new JTextField(30);
		input_m.setBounds(110, 60, 220, 20);
		c.add(input_m);
		// 출력 텍스트
		text_in = new JTextArea(30,30);
		js = new JScrollPane(text_in);
		js.setBounds(25, 90, 330, 200);
		c.add(js);
		// 내역 추가 버튼
		addBtn = new JButton("추가");
		addBtn.setBounds(100, 300, 180, 30);
		addBtn.addActionListener(this);
		c.add(addBtn);
		// 메뉴 돌아가기 버튼
		menuBtn = new JButton("메뉴로 돌아가기");
		menuBtn.setBounds(90, 350, 200, 40);
		menuBtn.addActionListener(this);
		c.add(menuBtn);
		
		c.setBackground(new Color(135, 206, 255));
		setBounds(650, 180, 395, 450);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		

		Object obj = e.getSource();
		
		// 내역 추가 버튼
		if(obj == addBtn) {
			// 빈 칸 확인.
			if(text_in.getText().equals("") ||input_m.getText().equals("") ) {
				JOptionPane.showMessageDialog(null, "금액이나 내용을 입력하지 않았습니다.");
				return;
			}
			// 금액, 내용
			int amount = Integer.parseInt(input_m.getText());
			String content = text_in.getText();
			
			
			String io_kind="";
			
			// radio1 : 수입 radio2 : 지출
			if(radio1.isSelected()) {
				io_kind="O";			
			}
			else if(radio2.isSelected()) {
				io_kind="I";
			}
			
			MemberDao dao = MemberDao.getInstance();
			// 접속한 id 
			String id = dao.getId();
			
			// 데이터 입력 후 true 추가 완료, false면 실패
			boolean insert_check= dao.input_data(id, io_kind, amount, content);
			
			if(insert_check) {			
				JOptionPane.showMessageDialog(null, "내역을 추가했습니다.");
				text_in.setText("");
				input_m.setText("");
			}
			else {
				JOptionPane.showMessageDialog(null, "내역을 추가에 실패하셨습니다.");	
			}
		}
		
		// 메뉴 돌아가기 버튼
		else if(obj == menuBtn) {
				new MenuView();
				this.dispose();
		}			
	}
}
