package UI;

import Core.*;
import com.messenger.MessageObserver;
import com.messenger.MessageWrapper;
import com.prereg.base.Course;
import com.prereg.base.PreRegMessageFactory;
import com.prereg.base.User;
import com.prereg.base.data.PreRegProto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;

import static Core.UICore.switchUI;

public class MainUI extends JFrame

{
    private PreRegClientSession clientSession;

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
    private JButton btnRefreshRequests;


    public DefaultListModel model;
    private DefaultListModel model2;
    public DefaultListModel model3;
    public DefaultListModel model4;


    private JList messageList;
    private JList schedList;
    private JList RequestList;
    private JScrollPane messageListPane;
    private JScrollPane schedListPane;
    private JScrollPane requestListPane;


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
    private JLabel lblRequest;

    private JLabel lblchangeTime;
    private JTextField tfTime;
    private JButton btnchangeTime;

    private JLabel lblcourseName;
    private JTextField tfcourseName;
    private JButton btncourseName;


    public MainUI() {
        setTitle("Login");
        setLayout(null);

        clientSession = PreRegClientSession.getSession();

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
        btnLogin.setBounds(85, 310, 100, 25);

        /* Course List Interface */
        btnRefreshRequests = new JButton("Refresh Requests");
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
        String[] searchByStrings = {"All Courses", "CRN", "Instructor Username", "Course Name", "Department"};
        cbSearchBy = new JComboBox(searchByStrings);
        tfSearch = new JTextField();
        model3 = new DefaultListModel();
        model4 = new DefaultListModel();
        schedList = new JList(model3);
        schedListPane = new JScrollPane(schedList);
        lblSched = new JLabel("Schedule:");
        RequestList = new JList(model4);
        requestListPane = new JScrollPane(RequestList);
        lblRequest = new JLabel("Requests:");

        lblchangeTime = new JLabel("Time:");
        tfTime = new JTextField();
        btnchangeTime = new JButton("Change Time");

        lblcourseName = new JLabel("Course Name:");
        tfcourseName = new JTextField();
        btncourseName = new JButton("Open Course");

        coursePanel.add(CRNTextField);
        coursePanel.add(CRNLabel);
        coursePanel.add(lblUName);
        coursePanel.add(lblTitles);
        coursePanel.add(courseListPane);
        coursePanel.add(lblSearch);
        coursePanel.add(cbSearchBy);
        coursePanel.add(btnSearch);
        coursePanel.add(tfSearch);


        CRNLabel.setBounds(15, 40, 50, 25);
        CRNTextField.setBounds(50, 40, 75, 25);
        lblUName.setBounds(15, 10, 240, 25);
        lblTitles.setBounds(15, 205, 600, 25);
        courseListPane.setBounds(10, 230, 720, 200);
        schedListPane.setBounds(15, 530, 250, 200);
        requestListPane.setBounds(15, 530, 250, 200);

        lblSched.setBounds(15, 465, 100, 100);
        lblRequest.setBounds(15, 465, 100, 100);

        btnRefreshSchedule.setBounds(115, 500, 150, 25);
        btnRefreshRequests.setBounds(115, 500, 150, 25);


        //btnMessages.setBounds(390, 10, 100, 25);
        btnCapacityRequest.setBounds(15, 70, 135, 25);

        TimeChangeLabel.setBounds(15, 100, 185, 25);
        TimeChangeTextField.setBounds(200, 100, 150, 25);
        btnTimeChangeRequest.setBounds(360, 100, 185, 25);


        lblchangeTime.setBounds(15, 75, 185, 25);
        tfTime.setBounds(55, 75, 150, 25);
        btnchangeTime.setBounds(210, 75, 110, 25);

        lblcourseName.setBounds(15, 110, 185, 25);
        tfcourseName.setBounds(115, 110, 150, 25);
        btncourseName.setBounds(280, 110, 110, 25);


        ProfUNameLabel.setBounds(15, 130, 175, 25);
        ProfUNameTextField.setBounds(200, 130, 125, 25);

        CourseRequestLabel.setBounds(15, 165, 260, 25);
        CourseRequestTextField.setBounds(255, 165, 125, 25);
        btnCourseRequest.setBounds(385, 165, 125, 25);

        lblSearch.setBounds(15, 440, 80, 25);
        cbSearchBy.setBounds(100, 440, 150, 25);
        tfSearch.setBounds(270, 440, 150, 25);
        btnSearch.setBounds(430, 440, 150, 25);


        /* Messages Interface */

        model2 = new DefaultListModel();
        messageList = new JList(model2);
        messageListPane = new JScrollPane(messageList);
        btnCourses = new JButton("Courses");


        messagePanel.add(btnCourses);
        messagePanel.add(messageListPane);
        btnCourses.setBounds(390, 10, 100, 25);

        loginPanel.setBounds(230, 0, 550, 500);
        coursePanel.setBounds(950, 0, 750, 1000);

        lblUName.setFont(new Font("sansserif", Font.BOLD, 14));
        lblTitles.setFont(new Font("sansserif", Font.BOLD, 12));
        lblTitles.setText("CRN  Section    Instructor  Course    Time                      Location     Active   MaxCap");

        add(loginPanel);
        add(coursePanel);

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
        btnRefreshRequests.addActionListener(actListener);
        btnchangeTime.addActionListener(actListener);

        txtUsername.addKeyListener(loginKeyListener);
        txtPassword.addKeyListener(loginKeyListener);

        addWindowListener(winListener);
    }

    public void AddCourseInterface() {
        if (isProfessor) {
            coursePanel.add(btnRefreshRequests);
            coursePanel.add(lblRequest);
            coursePanel.add(requestListPane);
            coursePanel.add(lblchangeTime);
            coursePanel.add(tfTime);
            coursePanel.add(btnchangeTime);
            coursePanel.add(btncourseName);
            coursePanel.add(lblcourseName);
            coursePanel.add(tfcourseName);

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

    public void setUserInfo(int id, String username, String title) {

//        User.init(id, username, title);
        lblUName.setText(clientSession.getUser().getUserName());

        updateUITitle();
    }

    public void updateUITitle() {
//        setTitle(clientSession.getUser()..getUITitle());
    }

    public void enableLoginInput(boolean enable) {
        txtUsername.setEnabled(enable);
        txtPassword.setEnabled(enable);
    }

    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public void addCourse(String s) {
        model.addElement(s);
    }

    public void login() {
        enableLoginInput(false);

        if (txtUsername.getText().equals("")) {
            UICore.showMessageDialog("Please enter your username.", "Error", JOptionPane.ERROR_MESSAGE);
            enableLoginInput(true);
            return;
        }

        if (txtPassword.getPassword().length == 0) {
            UICore.showMessageDialog("Please enter your password.", "Error", JOptionPane.ERROR_MESSAGE);
            enableLoginInput(true);
            return;
        }

        //NetworkManager.login(txtUsername.getText(), new String(txtPassword.getPassword()));
        try {
            clientSession.init(txtUsername.getText(), String.valueOf(txtPassword.getPassword()), new MessageObserver() {
                @Override
                public void notify(MessageWrapper messageWrapper) {
                    switch (messageWrapper.getMessageCode()) {
                        case PreRegMessageFactory.LOGIN_SUCCESS: /* Login is success */
                            PreRegProto.LoginResponseData responseData = (PreRegProto.LoginResponseData) messageWrapper.getMessage();
                            PreRegProto.UserData userData = responseData.getUserData();
                            int accountGuid = userData.getId();
                            System.out.println(accountGuid);
                            String accountUsername = userData.getUsername();
                            System.out.println(accountUsername);
                            String accountTitle = userData.getAccess().equals(PreRegProto.UserData.Access.PROFESSOR) ? "Professor" : "Student";
                            System.out.println(accountTitle);
                            clientSession.start();


                            if (accountTitle.equals("Student"))
                                MainUI.isProfessor = false;

                            else if (accountTitle.equals("Professor"))
                                MainUI.isProfessor = true;

                            new Thread(new UISwitcher()).start();
                            UICore.getMainUI().setUserInfo(accountGuid, accountUsername, accountTitle);
                            UICore.switchUI();
                            break;
                        case PreRegMessageFactory.LOGIN_FAILURE: /* Login failed */

                            UICore.showMessageDialog("The information you entered is not valid.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                            UICore.getMainUI().enableLoginInput(true);

                            break;

                        default: /* Server problem? */
                            UICore.showMessageDialog("Unknown error occur, please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
                            UICore.getMainUI().enableLoginInput(true);
                            break;
                    }

                    AddCourseInterface();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void resetUI() {
        if (isLoginUI) {
            txtPassword.setText("");
        } else {
            model.clear();

        }
    }


    ActionListener actListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(btnLogin))
                login();

            if (e.getSource().equals(btnCourses)) {
                isCourseUI = true;
                switchUI();
            }

            if (e.getSource().equals(btnCapacityRequest)) {
                if (isNumeric(CRNTextField.getText())) {
                    int CRN = Integer.parseInt(CRNTextField.getText().trim());
                    MessageWrapper messageWrapper = (new PreRegMessageFactory()).createMessage(PreRegMessageFactory.CAPACITY_REQUEST);
                    PreRegProto.CourseData courseData = PreRegProto.CourseData.newBuilder().setCRN(CRN).build();
                    messageWrapper.setMessage(courseData);
                    try {
                        clientSession.queueMessage(messageWrapper, new MessageObserver() {
                            @Override
                            public void notify(MessageWrapper messageWrapper) {
                                if (messageWrapper.getMessageType().equals(PreRegMessageFactory.REPLY_MESSAGE)) {
                                    UICore.showMessageDialog(((PreRegProto.ReplyMessage) messageWrapper.getMessage()).getReplyMessage(), "", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        });
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }

            if (e.getSource().equals(btnTimeChangeRequest)) {
                if (!TimeChangeTextField.getText().equals("") && isNumeric(CRNTextField.getText())) {
                    String times = TimeChangeTextField.getText();
                    int CRN = Integer.parseInt(CRNTextField.getText().trim());
                    MessageWrapper messageWrapper = (new PreRegMessageFactory()).createMessage(PreRegMessageFactory.CHANGE_TIME_REQUEST);
                    PreRegProto.CourseData courseData = PreRegProto.CourseData.newBuilder().setCRN(CRN).setTime(times).build();
                    messageWrapper.setMessage(courseData);
                    try {
                        clientSession.queueMessage(messageWrapper, new MessageObserver() {
                            @Override
                            public void notify(MessageWrapper messageWrapper) {
                                if (messageWrapper.getMessageType().equals(PreRegMessageFactory.REPLY_MESSAGE)) {
                                    UICore.showMessageDialog(((PreRegProto.ReplyMessage) messageWrapper.getMessage()).getReplyMessage(), "", JOptionPane.ERROR_MESSAGE);

                                }
                            }
                        });
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }

            if (e.getSource().equals(btnCourseRequest)) {
                if (!ProfUNameTextField.getText().equals("") && !CourseRequestTextField.getText().equals("")) {

                    String courseReq = CourseRequestTextField.getText();
                    String instructorUserName = ProfUNameTextField.getText();
                    MessageWrapper messageWrapper = (new PreRegMessageFactory()).createMessage(PreRegMessageFactory.OPEN_COURSE_REQUEST);
                    PreRegProto.CourseData courseData = PreRegProto.CourseData.newBuilder().setCourseName(courseReq)
                            .setInstructor(PreRegProto.UserData.newBuilder().setUsername(instructorUserName).build()).build();
                    messageWrapper.setMessage(courseData);
                    try {
                        clientSession.queueMessage(messageWrapper, new MessageObserver() {
                            @Override
                            public void notify(MessageWrapper messageWrapper) {
                                if (messageWrapper.getMessageType().equals(PreRegMessageFactory.REPLY_MESSAGE)) {
                                    UICore.showMessageDialog(((PreRegProto.ReplyMessage) messageWrapper.getMessage()).getReplyMessage(), "", JOptionPane.ERROR_MESSAGE);

                                }

                            }
                        });
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }

            if (e.getSource().equals(btnSearch)) {
                model.clear();
                String searchTerm = tfSearch.getText();

                MessageWrapper messageWrapper = (new PreRegMessageFactory()).createMessage(PreRegMessageFactory.COURSES_SEARCH);
                PreRegProto.CourseData.Builder queryBuilder = PreRegProto.CourseData.newBuilder();
                if (cbSearchBy.getSelectedItem().toString().equals("CRN")) {
                    queryBuilder.setCRN(Integer.parseInt(searchTerm));
                }
                PreRegProto.CourseData courseData = queryBuilder.build();
                messageWrapper.setMessage(courseData);
                try {
                    clientSession.queueMessage(messageWrapper, new MessageObserver() {
                        @Override
                        public void notify(MessageWrapper messageWrapper) {
                            model.clear();
                            java.util.List<PreRegProto.CourseData> courses = ((PreRegProto.CourseList) messageWrapper.getMessage()).getCourseList();

                            for (PreRegProto.CourseData courseData : courses) {

                                String course = courseData.getCRN() + "   " + courseData.getSectionNumber() +
                                        "                    " + courseData.getInstructor().getUsername() + "      " +
                                        courseData.getCourseName() + "       " + courseData.getTime() + "       " + courseData.getClassRoom()
                                        + "       " + courseData.getCapacity() + "        " + courseData.getMaxCapacity();
                                addCourse(course);

                            }

                        }
                    });
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }

            if (e.getSource().equals(btnRefreshSchedule)) {
                MessageWrapper messageWrapper = (new PreRegMessageFactory()).createMessage(PreRegMessageFactory.GET_SCHEDULE_REQUEST);
                try {
                    clientSession.queueMessage(messageWrapper, new MessageObserver() {
                        @Override
                        public void notify(MessageWrapper messageWrapper) {
                            model3.clear();
                            java.util.List<PreRegProto.CourseData> scheds = ((PreRegProto.CourseList) messageWrapper.getMessage()).getCourseList();

                            for (PreRegProto.CourseData courseData : scheds) {
                                String sched = courseData.getCourseName() + "  " + courseData.getTime();
                                model3.addElement(sched);
                            }
                        }
                    });
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
//            if(e.getSource().equals(btnRefreshRequests)){ //TODO:Refresh Requests for Professor
//                //MessageWrapper messageWrapper = (new PreRegMessageFactory()).createMessage(PreRegMessageFactory.GET_REQUEST_LIST);
//                try {
//                    clientSession.queueMessage(messageWrapper, new MessageObserver() {
//                        @Override
//                        public void notify(MessageWrapper messageWrapper) {
//                            model4.clear();
//                            java.util.List<PreRegProto.CourseData> reqs = ((PreRegProto.CourseList) messageWrapper.getMessage()).getCourseList();
//                            //TODO:Request List
//                            for (PreRegProto.CourseData courseData : reqs) {
//                                String sched = courseData.getCourseName() + "  " + courseData.getTime(); //TODO: getType and getInfo to print them
//                                model4.addElement(sched);
//                            }
//                        }
//                    });
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
//
//            }
            if(e.getSource().equals(btnchangeTime)){  //TODO: change course time (by Professor)
                if (!tfTime.getText().equals("") && isNumeric(CRNTextField.getText())) {
                    String times = tfTime.getText();
                    int CRN = Integer.parseInt(CRNTextField.getText().trim());
                    MessageWrapper messageWrapper = (new PreRegMessageFactory()).createMessage(PreRegMessageFactory.CHANGE_TIME_REQUEST);
                    //Change Time message in messageWrapper to send to database for it to execute
                    PreRegProto.CourseData courseData = PreRegProto.CourseData.newBuilder().setCRN(CRN).setTime(times).build();
                    messageWrapper.setMessage(courseData);
                    try {
                        clientSession.queueMessage(messageWrapper, new MessageObserver() {
                            @Override
                            public void notify(MessageWrapper messageWrapper) {
                                if (messageWrapper.getMessageType().equals(PreRegMessageFactory.REPLY_MESSAGE)) {
                                    UICore.showMessageDialog(((PreRegProto.ReplyMessage) messageWrapper.getMessage()).getReplyMessage(), "", JOptionPane.ERROR_MESSAGE);

                                }
                            }
                        });
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }

            }

            if(e.getSource().equals(btncourseName)){  //TODO: Open course by Professor
                if (!tfcourseName.getText().equals("")) {
                    String times = tfcourseName.getText();
                    MessageWrapper messageWrapper = (new PreRegMessageFactory()).createMessage(PreRegMessageFactory.CHANGE_TIME_REQUEST);
                    //Change Time message in messageWrapper to send to database for it to execute
                    PreRegProto.CourseData courseData = PreRegProto.CourseData.newBuilder().setTime(times).build();
                    messageWrapper.setMessage(courseData);
                    //TODO:We also need to set a classroom and a CRN at the server. Do it in a simple way, we'll fix it later.
                    try {
                        clientSession.queueMessage(messageWrapper, new MessageObserver() {
                            @Override
                            public void notify(MessageWrapper messageWrapper) {
                                if (messageWrapper.getMessageType().equals(PreRegMessageFactory.REPLY_MESSAGE)) {
                                    UICore.showMessageDialog(((PreRegProto.ReplyMessage) messageWrapper.getMessage()).getReplyMessage(), "", JOptionPane.ERROR_MESSAGE);

                                }
                            }
                        });
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }

            }
        }
    };


    KeyListener loginKeyListener = new KeyAdapter() {
        public void keyReleased(KeyEvent e) {
            // Only handle enter key in Chat Interface.
            if (e.getKeyCode() == KeyEvent.VK_ENTER)
                login();
        }
    };

    WindowListener winListener = new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
            // Only logout when client is logged in
            if (!isLoginUI)
//                NetworkManager.SendPacket(new Packet(CMSG_LOGOUT));

                System.exit(0);
        }
    };

    public static final class UISwitcher implements Runnable {
        public void run() {
            int movement = -3;
            if (isLoginUI) {
                isLoginUI = !isLoginUI;
                movement = -3;
            } else if (isCourseUI) {
                isCourseUI = !isCourseUI;
                movement = 3;
            }

            for (int i = 0; i < 162; i++) {
                loginPanel.setLocation(loginPanel.getX() + movement, 0);
                coursePanel.setLocation(coursePanel.getX() + movement, 0);

                try {
                    Thread.sleep(2);
                } catch (Exception e) {
                }
            }

        }
    }
}