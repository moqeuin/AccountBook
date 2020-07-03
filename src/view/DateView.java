package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import dao.MemberDao;
import dto.DataDto;

public class DateView extends JFrame implements ActionListener {
	
	private JButton menuButton;
	private JButton rsBtn;
	private JTextField[] date_in = new JTextField[6];
	private JLabel sig;
	private JTextArea rs_out;
	private JScrollPane js;
	
	private Container c;
	
	public DateView() {
		
		super("기간별 내역");
		
		c = getContentPane();
		c.setLayout(null);
		
		// 기간 입력 텍스트 
		
		for (int i = 0; i < 3; i++) {
			date_in[i] = new JTextField(2);
			date_in[i].setBounds(25+(i*70), 10, 60, 20);
			c.add(date_in[i]);
		}
		
		// ~ 표시
		sig = new JLabel("~");
		sig.setBounds(260, 10, 30, 20);
		sig.setFont(new Font("굴림체", Font.BOLD, 25));
		c.add(sig);
		
		// 기간 입력 텍스트 2
	
		for (int i = 3; i < 6; i++) {
			date_in[i] = new JTextField(2);
			date_in[i].setBounds(155+((i-3)*70), 40, 60, 20);
			c.add(date_in[i]);
		}
		
		// 결과보기 버튼
		rsBtn = new JButton("결과 보기");
		rsBtn.setBounds(140, 70, 100, 30);
		rsBtn.addActionListener(this);
		c.add(rsBtn);
		
		// 출력 텍스트
		rs_out = new JTextArea(30,30);
		js = new JScrollPane(rs_out);
		js.setBounds(25, 110, 330, 220);
		c.add(js);
		
		// 메뉴 돌아가기 버튼
		menuButton = new JButton("메뉴로 돌아가기");
		menuButton.setBounds(90, 350, 200, 40);
		menuButton.addActionListener(this);
		c.add(menuButton);
		
		c.setBackground(new Color(135, 206, 255));
		setBounds(650, 180, 395, 450);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		JButton btn = (JButton)e.getSource();
		String btnTitle = btn.getLabel();
		
		MemberDao dao = MemberDao.getInstance();
		// 접속한 id
		String id = dao.getId();
		
		// DB로부터 받아온 데이터를 리스트로 저장.
		List<DataDto> list = new ArrayList<DataDto>();
		
		// 결과 보기 버튼
		if( btnTitle.equals("결과 보기")) {
			
			// 빈 칸 확인
			for (int i = 0; i < date_in.length; i++) {	
				if(date_in[i].getText().equals("")) {
					JOptionPane.showMessageDialog(null, "입력하지 않은 내용이 있습니다.");
					return;
				}
			}
			
			rs_out.setText("");
			
			// 텍스트에 입력한 데이터를 배열에 저장.
			String[] date = new String[6];
			for (int i = 0; i < date.length; i++) {
				date[i] = date_in[i].getText();
			}
			
			// 쿼리문에서 하나의 문자열로 입력시키 위해서 각 텍스트를 더 해준다.
			String date1 = date[0] + date[1] + date[2];
			String date2 = date[3] + date[4] + date[5];
			// DB 입력해서 dto로 리스트에 결과를 저장.
			list = dao.date_result(id, date1, date2);
			// 출력
			for(DataDto data : list) {
				rs_out.append(data.toString()+"\n");
			}
		}
		// 메뉴 버튼
		else if(btnTitle.equals("메뉴로 돌아가기")) {
			    new MenuView();
			    this.dispose();
		}
	}
}
