package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MenuView extends JFrame implements ActionListener {
	
	private JLabel menuLabel;
	private JButton menuBtn[] = new JButton[3];
	
	private Container c;
		
	public MenuView() {
		super("메뉴화면");
			
		c = getContentPane();
		c.setLayout(null);
		
		// 뷰 이름
		menuLabel = new JLabel("메뉴");
		menuLabel.setBounds(106, 20, 120, 20);
		menuLabel.setHorizontalAlignment(JLabel.CENTER);
		c.add(menuLabel);
		
		// 버튼 생성
		String str[] = {"추가", "기간별 내역", "항목별 검색"};
		for (int i = 0; i < str.length; i++) {
			menuBtn[i] = new JButton(str[i]);
			menuBtn[i].setBounds(50, 80+(i*60), 230, 35);
			menuBtn[i].addActionListener(this);
			c.add(menuBtn[i]);
		}
		
		c.setBackground(new Color(135, 206, 255));
		setBounds(650, 180, 350, 320);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		JButton btn = (JButton)e.getSource();
		String btnTitle = btn.getLabel();
		// 추가 버튼
		if(btnTitle.equals("추가")) {
			new AddView();
			this.dispose();
		}
		// 기간별 내역 버튼
		else if(btnTitle.equals("기간별 내역")) {
			new DateView();
			this.dispose();
		}
		// 항목별 검색 버튼
		else if(btnTitle.equals("항목별 검색")) {
			new SearchView();
			this.dispose();
		}		
	}
}