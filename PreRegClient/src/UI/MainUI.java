package UI;

import Core.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static Core.UICore.switchUI;

public class MainUI extends JFrame implements MSG

{
    private static JPanel loginPanel;
    private static JPanel coursePanel;
    private static JPanel messagePanel;

    
    /* Menu */
    private static JMenuBar menuBar;


    private static boolean isLoginUI;
    private static boolean isCourseUI;
    private static boolean isMessageUI;
    
    /* Login Interface */
    private JButton btnLogin;

    
    private JTextField txtUsername;
    private JPasswordField txtPassword;

    private JLabel lblUsername;
    private JLabel lblPassword;

    /* Course List Interface */
    private JList courseList;
    private JScrollPane courseListPane;
    public JComboBox cbSearchBy;

    //private JButton btnMessages;
    private JButton btnCourses;

    private JLabel lblUName;
    private JLabel CRNLabel;
    private JLabel lblSearch;

    private JTextField CRNTextField;

    private JButton btnCapacityRequest;
    private JButton btnRefreshSchedule;

    public  DefaultListModel model;
    private DefaultListModel model2;
    public DefaultListModel model3;

    private JList messageList;
    private JList schedList;
    private JScrollPane messageListPane;
    private JScrollPane schedListPane;

    private JLabel lblTitles;

    public static boolean isProfessor;

    private JButton btnTimeChangeRequest;
    private JTextField TimeChangeTextField;
    private JLabel TimeChangeLabel;

    private JButton btnCourseRequest;
    private JTextField CourseRequestTextField;
    private JLabel CourseRequestLabel;

    private JTextField ProfUNameTextField;
    private JLabel ProfUNameLabel;

    private JButton btnSearch;
    private JTextField tfSearch;

    private JLabel lblSched;

    public MainUI()
    {
        setTitle("Login");
        setLayout(null);
        
        isLoginUI = true;
        
        loginPanel = new JPanel(null);
        coursePanel = new JPanel(null);
        messagePanel = new JPanel(null);
        
        menuBar = new JMenuBar();

        /* Login UI */
        lblUsername = new JLabel("Username");
        lblPassword = new JLabel("Password");
        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        btnLogin = new JButton("Login");
        
        loginPanel.add(lblPassword);
        loginPanel.add(lblUsername);
        loginPanel.add(txtPassword);
        loginPanel.add(txtUsername);
        loginPanel.add(btnLogin);


        lblPassword.setBounds(25, 180, 100, 25);
        lblUsername.setBounds(25, 130, 100, 25);
        txtPassword.setBounds(25, 200, 220, 25);
        txtUsername.setBounds(25, 150, 220, 25);
        btnLogin.setBounds(85,310, 100, 25);

        /* Course List Interface */

          lblUName = new JLabel();
          lblTitles = new JLabel();
          CRNTextField = new JTextField();
          CRNLabel = new JLabel("CRN: ");
          TimeChangeLabel = new JLabel("Requested Days and Time: ");
          TimeChangeTextField = new JTextField();
          btnTimeChangeRequest = new JButton("Time Change Request");
          ProfUNameLabel = new JLabel("Instructor User Name: ");
          ProfUNameTextField = new JTextField();
          btnRefreshSchedule = new JButton("Refresh Schedule");

        model = new DefaultListModel();
        courseList = new JList(model);
        courseListPane = new JScrollPane(courseList);
        //btnMessages = new JButton("Messages");
        btnCapacityRequest = new JButton("Capacity Request");
        CourseRequestTextField = new JTextField();
        CourseRequestLabel = new JLabel("New Course Name (e.g: XXXX 123): ");
        btnCourseRequest = new JButton("Request Course");
        lblSearch = new JLabel("Search By:");
        btnSearch = new JButton("Search");
        String[] searchByStrings = {"CRN","Instructor Username","Course Name", "Department"};
        cbSearchBy = new JComboBox(searchByStrings);
        tfSearch = new JTextField();
        model3 = new DefaultListModel();
        schedList = new JList(model3);
        schedListPane = new JScrollPane(schedList);
        lblSched = new JLabel("Schedule:");

        coursePanel.add(CRNTextField);
        coursePanel.add(CRNLabel);
        coursePanel.add(lblUName);
        coursePanel.add(lblTitles);
        coursePanel.add(courseListPane);
        //coursePanel.add(btnMessages);
        coursePanel.add(lblSearch);
        coursePanel.add(cbSearchBy);
        coursePanel.add(btnSearch);
        coursePanel.add(tfSearch);



        CRNLabel.setBounds(15,40,50,25);
        CRNTextField.setBounds(50, 40,75,25);
        lblUName.setBounds(15,10, 240,25);
        lblTitles.setBounds(15, 205, 600, 25);
        courseListPane.setBounds(10, 230, 720, 200);
        schedListPane.setBounds(15,530,250,200);
        lblSched.setBounds(15,465,100,100);
        btnRefreshSchedule.setBounds(115,500,150,25);


        //btnMessages.setBounds(390, 10, 100, 25);
        btnCapacityRequest.setBounds(15, 70, 135,25);

        TimeChangeLabel.setBounds(15, 100, 185, 25);
        TimeChangeTextField.setBounds(200, 100, 150, 25);
        btnTimeChangeRequest.setBounds(360, 100, 185, 25);

        ProfUNameLabel.setBounds(15,130,175,25);
        ProfUNameTextField.setBounds(200, 130,125,25);

        CourseRequestLabel.setBounds(15, 165, 260, 25);
        CourseRequestTextField.setBounds(255, 165, 125, 25);
        btnCourseRequest.setBounds(385, 165, 125, 25);

        lblSearch.setBounds(15,440,80,25);
        cbSearchBy.setBounds(100,440,150,25);
        tfSearch.setBounds(270,440,150,25);
        btnSearch.setBounds(430,440,150,25);


        /* Messages Interface */

        model2 = new DefaultListModel();
        messageList = new JList(model2);
        messageListPane = new JScrollPane(messageList);
        btnCourses = new JButton("Courses");

        messageListPane.setBounds(400, 200, 1200, 330);


        messagePanel.add(btnCourses);
        messagePanel.add(messageListPane);
        btnCourses.setBounds(390, 10, 100, 25);

        loginPanel.setBounds(230, 0, 550, 500);
        coursePanel.setBounds(950, 0, 750, 1000);
        messagePanel.setBounds(1500, 0, 1100, 475);
       
        lblUName.setFont(new Font("sansserif", Font.BOLD, 14));
        lblTitles.setFont(new Font("sansserif", Font.BOLD, 12));
        lblTitles.setText("CRN  Section    Instructor  Course    Time                      Location     Active   MaxCap");

        add(loginPanel);
        add(coursePanel);
        add(messagePanel);
        
        setJMenuBar(menuBar);
        setSize(750, 750);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        
        btnLogin.addActionListener(actListener);
        //btnMessages.addActionListener(actListener);
        btnCourses.addActionListener(actListener);
        btnCapacityRequest.addActionListener(actListener);
        btnTimeChangeRequest.addActionListener(actListener);
        btnCourseRequest.addActionListener(actListener);
        btnSearch.addActionListener(actListener);
        btnRefreshSchedule.addActionListener(actListener);
        
        txtUsername.addKeyListener(loginKeyListener);
        txtPassword.addKeyListener(loginKeyListener);

        addWindowListener(winListener);
    }

    public  void AddCourseInterface() {
        if (isProfessor) {

        }

        if (!isProfessor) {
            coursePanel.add(btnCapacityRequest);
            coursePanel.add(TimeChangeLabel);
            coursePanel.add(TimeChangeTextField);
            coursePanel.add(btnTimeChangeRequest);
            coursePanel.add(btnCourseRequest);
            coursePanel.add(CourseRequestTextField);
            coursePanel.add(CourseRequestLabel);
            coursePanel.add(ProfUNameLabel);
            coursePanel.add(ProfUNameTextField);
            coursePanel.add(schedListPane);
            coursePanel.add(lblSched);
            coursePanel.add(btnRefreshSchedule);
        }
    }

    public void setUserInfo(int id, String username, String title){

        User.init(id, username, title);
        lblUName.setText(User.getUsername());

        updateUITitle();
    }
    
    public void updateUITitle()
    {
        setTitle(User.getUITitle());
    }
    
    public void enableLoginInput(boolean enable)
    {
        txtUsername.setEnabled(enable);
        txtPassword.setEnabled(enable);
    }
    public static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

    public void addCourse(Course c){
        model.addElement(c);
    }

    public void login()
    {
        enableLoginInput(false);
        
        if (txtUsername.getText().equals(""))
        {
            UICore.showMessageDialog("Please enter your username.", "Error", JOptionPane.ERROR_MESSAGE);
            enableLoginInput(true);
            return;
        }

        if (txtPassword.getPassword().length == 0)
        {
            UICore.showMessageDialog("Please enter your password.", "Error", JOptionPane.ERROR_MESSAGE);
            enableLoginInput(true);
            return;
        }

        NetworkManager.login(txtUsername.getText(), new String(txtPassword.getPassword()));
        AddCourseInterface();
    }
    

    public void resetUI()
    {
        if (isLoginUI)
        {
            txtPassword.setText("");
        }
        else
        {
            model.clear();

        }
    }


    ActionListener actListener = new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource().equals(btnLogin))
                login();
//            if(e.getSource().equals(btnMessages)) {
//                isMessageUI = true;
//                switchUI();
//            }

            if(e.getSource().equals(btnCourses)){
                isCourseUI = true;
                switchUI();
            }

            if(e.getSource().equals(btnCapacityRequest)){
                if(isNumeric(CRNTextField.getText().toString())) {
                    int CRN = Integer.parseInt(CRNTextField.getText().toString().trim());

                    Packet p = new Packet(CMSG_CAPACITY_REQUEST);
                    p.put(CRN);
                    NetworkManager.SendPacket(p);
                }
            }

            if(e.getSource().equals(btnTimeChangeRequest)){
                if(!TimeChangeTextField.getText().equals("") && isNumeric(CRNTextField.getText().toString())){
                    String times = TimeChangeTextField.getText();
                    int CRN = Integer.parseInt(CRNTextField.getText().toString().trim());

                    Packet p = new Packet(CMSG_TIME_REQUEST);
                    p.put(CRN);
                    p.put(times);
                    NetworkManager.SendPacket(p);
                }
            }

            if(e.getSource().equals(btnCourseRequest)){
                if(!ProfUNameTextField.getText().equals("") && !CourseRequestTextField.getText().equals("")){

                    String courseReq = CourseRequestTextField.getText();
                    String instructorUserName = ProfUNameTextField.getText();
                    Packet p = new Packet(CMSG_COURSE_REQUEST);
                    p.put(courseReq);
                    p.put(instructorUserName);
                    NetworkManager.SendPacket(p);
                }
            }

            if(e.getSource().equals(btnSearch)){
                model.clear();

                Packet p = new Packet(CMSG_COURSE_SEARCH);

                p.put(cbSearchBy.getSelectedItem());

                if(tfSearch.getText().trim().equals(""))
                    p.put("All Courses");
                else
                p.put(tfSearch.getText());

                NetworkManager.SendPacket(p);
            }

            if(e.getSource().equals(btnRefreshSchedule)){
                model3.clear();
                Packet p = new Packet(CMSG_GET_SCHED_LIST);
                NetworkManager.SendPacket(p);
            }


        }
    };

    
    KeyListener loginKeyListener = new KeyAdapter()
    {
        public void keyReleased(KeyEvent e)
        {
            // Only handle enter key in Chat Interface.
            if (e.getKeyCode() == KeyEvent.VK_ENTER)
                login();
        }
    };
    
    WindowListener winListener = new WindowAdapter()
    {
        public void windowClosing(WindowEvent e) 
        {
            // Only logout when client is logged in
            if (!isLoginUI)
                NetworkManager.SendPacket(new Packet(CMSG_LOGOUT));
            
            System.exit(0);
        }
    };
    
    public static final class UISwitcher implements Runnable
    {
        public void run()
        {
            int movement = -3;
            if(isLoginUI) {
                isLoginUI = !isLoginUI;
                movement = -3;
            }
            else if(isCourseUI) {
                isCourseUI = !isCourseUI;
                movement = 3;
            }
            else if(isMessageUI){
                isMessageUI = !isMessageUI;
                movement = -3;
            }

            for(int i = 0; i < 315; i++)
            {
                loginPanel.setLocation(loginPanel.getX() + movement, 0);
                coursePanel.setLocation(coursePanel.getX() + movement, 0);
                messagePanel.setLocation(messagePanel.getX() + movement, 0);
                
                try { Thread.sleep(2); }
                catch(Exception e) {}
            }

        }
    }
}
