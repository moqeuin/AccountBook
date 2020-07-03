package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import dao.MemberDao;
import dto.DataDto;

public class SearchView extends JFrame implements ActionListener {
	
	private JButton menuButton;
	private JTextField search_txt;
	private JTextArea rs_out;	
	private JButton searchBtn;
	private JScrollPane js;
	
	private Container c;
	
	public SearchView() {
		super("항목별 검색");
		
		c = getContentPane();
		c.setLayout(null);
		
		// 찾을 키워드를 입력할 입력창
		search_txt = new JTextField(50);
		search_txt.setBounds(15, 20, 260, 30);
		c.add(search_txt);
		// 검색 버튼
		searchBtn = new JButton("검색");
		searchBtn.setBounds(285, 20, 80, 30);
		searchBtn.addActionListener(this);
		c.add(searchBtn);
		// 검색한 내용을 포함한 내역을 출력.
		rs_out = new JTextArea(30, 30);
		js = new JScrollPane(rs_out);
		js.setBounds(15, 60, 350, 270);
		c.add(js);
		// 메뉴 버튼
		menuButton = new JButton("메뉴로 돌아가기");
		menuButton.setBounds(90, 350, 200, 40);
		menuButton.addActionListener(this);
		c.add(menuButton);
		
		c.setBackground(new Color(135, 206, 255));
		setBounds(650, 180, 396, 450);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton)e.getSource();
		String btnTitle = btn.getLabel();
		MemberDao dao = MemberDao.getInstance();
		
		List<DataDto> list = new ArrayList<DataDto>();
		// 검색 버튼
		if(btnTitle.equals("검색")) {
			// 전에 출력한 내용이 있을수 있기 때문에 출력창을 초기화
			rs_out.setText("");
			// 접속한 사람의 id
			String id = dao.getId();
			// 찾을 키워드를 변수에 저장
			String search = search_txt.getText().trim();
			// 입력창이 빈 칸인지 확인.
			if(search.equals("")) {
				JOptionPane.showMessageDialog(null, "검색어를 입력하지 않았습니다.");
				return;
			}
			// 찾은 데이터를 저장한 리스트를 저장.
			list = dao.txt_search(id, search);
			
			for(DataDto data : list) {
				// textarea에 출력.
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
