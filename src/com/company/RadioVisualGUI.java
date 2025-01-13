package com.company;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class RadioVisualGUI {

    //radio
    Radio radio;
    Thread th;
    JSlider volumeManager;
    int actualVolume = -30;

    // date
   SimpleDateFormat dateFormat;
   Calendar calendar;
   String time;

    // frame
   private final JFrame RadioFrame;

   // panels
   private JPanel titlePanel;
   private JPanel leftSidePanel;
   private JPanel centralMainLeftPanel;
   private JPanel bottomPanel;
   private JPanel centralMainPanel;
   private JPanel editListPanel;

   //buttons
   private JButton RadioButtonStop;
   private JButton RadioButtonStart;

   private JButton buttonADD;
   private JButton buttonDELETE;
   private JButton buttonEDIT;
   //list

   private JLabel RadioNameOutput;
   private JScrollPane scroll;
   public Object ActualStation;

   //Icons
   final ImageIcon icon;

   //labels
    public JLabel dateLabel;

    final List<RadioHandler> stations = new LinkedList<>();
    DefaultListModel listOfStations = new DefaultListModel();
    JList<String> URLsList = new JList<String>();

    public int id;

    private String ActualUrlToPlay;

    public RadioVisualGUI() {
        RadioFrame = new JFrame("Radio v.01");
        titlePanel = new JPanel();
        bottomPanel = new JPanel();
        leftSidePanel = new JPanel();
        centralMainLeftPanel = new JPanel();
        centralMainPanel = new JPanel();
        editListPanel = new JPanel();
        volumeManager = new JSlider();

        icon = new ImageIcon("icon.png");

        radio = new Radio();

        // some stations
        RadioHandler r1 = new RadioHandler("Deutsch1", "https://streams.ilovemusic.de/iloveradio36.mp3");
        RadioHandler r2 = new RadioHandler("Deutsch2", "https://streams.ilovemusic.de/iloveradio1.mp3");
        RadioHandler r3 = new RadioHandler("Deutsch3", "https://streams.ilovemusic.de/iloveradio35.mp3");
        RadioHandler r4 = new RadioHandler("Deutsch4", "https://streams.ilovemusic.de/iloveradio10.mp3");
        RadioHandler r5 = new RadioHandler("RMF FM", "http://195.150.20.4:8000/rmf_fm");
        RadioHandler r6 = new RadioHandler("RMF Classic", "http://195.150.20.4:8000/rmf_classic");
        RadioHandler r7 = new RadioHandler("RMF Maxxx", "http://195.150.20.4:8000/rmf_maxxx");
        RadioHandler r8 = new RadioHandler("RMF Hip Hop", "http://195.150.20.4:8000/rmf_hiphop");
        RadioHandler r9 = new RadioHandler("RMF Love", "http://195.150.20.4:8000/rmf_love");
        RadioHandler r10 = new RadioHandler("RMF Club", "http://195.150.20.4:8000/rmf_club");
        RadioHandler r11 = new RadioHandler("RMF Groove", "http://195.150.20.4:8000/rmf_groove");
        RadioHandler r12 = new RadioHandler("Radio Zlote Przeboje", "http://poznan5-6.radio.pionier.net.pl:8000/tuba9-1.mp3");
        RadioHandler r13 = new RadioHandler("Radio party ", "http://s4.radioparty.pl:8000/rp.mp3");
        RadioHandler r14 = new RadioHandler("ESKA Goraca 20 ", "https://ic1.smcdn.pl/6130-1.mp3");
        RadioHandler r15 = new RadioHandler("ESKA Wroclaw", "https://ic2.smcdn.pl/2180-1.mp3");

        stations.add(r1);
        stations.add(r2);
        stations.add(r3);
        stations.add(r4);
        stations.add(r5);
        stations.add(r6);
        stations.add(r7);
        stations.add(r8);
        stations.add(r9);
        stations.add(r10);
        stations.add(r11);
        stations.add(r12);
        stations.add(r13);
        stations.add(r14);
        stations.add(r15);

        listOfStations.addElement(r1.getName());
        listOfStations.addElement(r2.getName());
        listOfStations.addElement(r3.getName());
        listOfStations.addElement(r4.getName());
        listOfStations.addElement(r5.getName());
        listOfStations.addElement(r6.getName());
        listOfStations.addElement(r7.getName());
        listOfStations.addElement(r8.getName());
        listOfStations.addElement(r9.getName());
        listOfStations.addElement(r10.getName());
        listOfStations.addElement(r11.getName());
        listOfStations.addElement(r12.getName());
        listOfStations.addElement(r13.getName());
        listOfStations.addElement(r14.getName());
        listOfStations.addElement(r15.getName());

        // start deklaracja
        RadioButtonStart = new JButton("start");
        RadioButtonStart.setPreferredSize(new Dimension(80, 30));

        // stop deklaracja
        RadioButtonStop = new JButton("stop");
        RadioButtonStop.setPreferredSize(new Dimension(80, 30));

        // add button
        buttonADD = new JButton("add");
        buttonADD.setBackground(Color.gray);
        buttonADD.setForeground(Color.white);

        // delete button
        buttonDELETE = new JButton("delete");
        buttonDELETE.setBackground(Color.gray);
        buttonDELETE.setForeground(Color.white);

        // edit button
        buttonEDIT = new JButton("edit");
        buttonEDIT.setBackground(Color.gray);
        buttonEDIT.setForeground(Color.white);

        // data Utils
        dateFormat = new SimpleDateFormat("hh:mm");

        URLsList.setModel(listOfStations);
        URLsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scroll = new JScrollPane(URLsList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setPreferredSize(new Dimension(100, 300));

        // slider
        volumeManager = new JSlider(-70, 6, -30);
        volumeManager.setPaintTicks(true);
        volumeManager.setMinorTickSpacing(10);
        volumeManager.setPaintTrack(true);
        volumeManager.setMajorTickSpacing(25);
        volumeManager.setFont(new Font("Bakery Font", Font.PLAIN, 15));

        volumeManager.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                actualVolume = volumeManager.getValue();
                radio.setVolume(actualVolume);
            }
        });

        // name of station
        RadioNameOutput = new JLabel("actual station : ");
        RadioNameOutput.setBackground(new Color(0xCDE000));
//        RadioNameOutput.setBounds(150, 200, 250, 70);
        RadioNameOutput.setPreferredSize(new Dimension(200,70));
        RadioNameOutput.setOpaque(true);
        RadioNameOutput.setHorizontalTextPosition(JLabel.CENTER); // set text left,center,right
        RadioNameOutput.setVerticalTextPosition(JLabel.CENTER); // set texttop center or bottom
        RadioNameOutput.setForeground(new Color(0xB40A4F)); // color text
        RadioNameOutput.setFont(new Font("MV Boli", Font.PLAIN, 16)); // set font of text
        RadioNameOutput.setVerticalAlignment(JLabel.CENTER); // set vertical position icon + text within label
        RadioNameOutput.setHorizontalAlignment(JLabel.CENTER); // set horizontal position icon + text within label

        // panels
        titlePanel.setBackground(new Color(17, 55, 134));
        titlePanel.setBounds(100, 0, 600, 100);
        titlePanel.setLayout(null);

        leftSidePanel.setBackground(new Color(121, 14, 26, 229));
        leftSidePanel.setBounds(0, 0, 100, 700);
        leftSidePanel.setLayout(null);

        centralMainLeftPanel.setBackground(Color.GRAY);
        centralMainLeftPanel.setBounds(100, 100, 100, 400);
        centralMainLeftPanel.setLayout(new GridLayout(0, 1));

        bottomPanel.setBackground(new Color(0, 0, 0, 192));
        bottomPanel.setBounds(100, 600, 600, 100);
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        centralMainPanel.setBackground(new Color(0, 0, 0, 204));
        centralMainPanel.setBounds(200, 100, 500, 500);
        centralMainPanel.setLayout(new BorderLayout());

        centralMainPanel.add(RadioNameOutput, BorderLayout.NORTH);

        editListPanel.setBackground(new Color(0, 0, 0, 192));
        editListPanel.setBounds(100, 501, 100, 99);
        editListPanel.setLayout(new GridLayout(3, 1));

        // labels
        dateLabel = new JLabel();
        dateLabel.setBounds(550, 0, 50, 30);
        dateLabel.setForeground(Color.PINK);

        URLsList.addListSelectionListener( new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                ActualStation = URLsList.getSelectedValue();
                RadioNameOutput.setText("aktualnie wybrana stacja :  "  + ActualStation);
                 id = URLsList.getSelectedIndex();
                ActualUrlToPlay = stations.get(id).getUrl();
//        ActualUrlToPlay = (String) listOfStations.getElementAt(index);
            }
        });

        // frame add
        RadioFrame.add(titlePanel);
        RadioFrame.add(leftSidePanel);
        RadioFrame.add(bottomPanel);
        RadioFrame.add(centralMainLeftPanel);
        RadioFrame.add(centralMainPanel);
        RadioFrame.add(editListPanel);

        // panel add
        bottomPanel.add(RadioButtonStart);
        bottomPanel.add(RadioButtonStop);
        bottomPanel.add(volumeManager);
        titlePanel.add(dateLabel);

        centralMainLeftPanel.add(scroll);
        editListPanel.add(buttonADD);
        editListPanel.add(buttonDELETE);
        editListPanel.add(buttonEDIT);

        // declaration of setting
        RadioFrame.setSize(700, 700);
        RadioFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        RadioFrame.setLayout(null);
        RadioFrame.setLocation(100, 100);
        RadioFrame.setResizable(false);
        RadioFrame.setIconImage(icon.getImage());

        updateMethod();

        // Dimension
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        RadioFrame.setLocation(dim.getSize().width / 2 - RadioFrame.getSize().width / 2, dim.getSize().height / 2 - RadioFrame.getSize().height / 2);
        RadioFrame.setVisible(true);

        buttonADD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField field1 = new JTextField();
                JTextField field2 = new JTextField();

                Object[] fields = {
                        "give name" , field1,
                        "put URL of station", field2,
                };

                JOptionPane.showConfirmDialog(null, fields, "Radio header", JOptionPane.OK_CANCEL_OPTION);
                if(!field1.getText().isEmpty() && !field2.getText().isEmpty()) {
                    RadioHandler radd = new RadioHandler(field1.getText(), field2.getText());

                    stations.add(radd);
                    listOfStations.addElement(field1.getText());
                }
            }
        });

        buttonDELETE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == buttonDELETE) {
                    listOfStations.remove(URLsList.getSelectedIndex()  + 1);
                    stations.remove(URLsList.getSelectedIndex());
//                    int indexToDelete = 0;
//                   String result = JOptionPane.showInputDialog(null, indexToDelete, "Radio header", JOptionPane.OK_CANCEL_OPTION);
//                   int a = parseInt(result);
//                   if (indexToDelete == 0) {
//                       URLsList.setSelectedIndex(a);
//                       listOfStations.remove(Math.abs(URLsList.getSelectedIndex() + a));
//                       URLsList.setModel(listOfStations);
//
//                   }
//                }
                }
            }
        });

        buttonEDIT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField field2 = new JTextField();
                int index = URLsList.getSelectedIndex();
                String nameStation = URLsList.getSelectedValue();

                Object fields[] = {
                        "give name" , nameStation,
                        "put URL of station", field2,
                };

                JOptionPane.showConfirmDialog(null, fields, "Radio header", JOptionPane.OK_CANCEL_OPTION);
                if(!field2.getText().isEmpty()) {
                    RadioHandler radd = new RadioHandler(nameStation, field2.getText());

                    stations.set(index,radd);
                    System.out.println(index);
                    listOfStations.setElementAt(nameStation, index);
                }
            }
        });

        RadioButtonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!radio.isRunning()) {

                    th = new Thread(() -> {
                        radio.volControl = null;
                        radio.play(ActualUrlToPlay);
                    });
                    th.start();
                    System.out.println(th.getState());
                }
            }
        });

        RadioButtonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(th.getState());
                radio.stop();
                radio.volControl = null;
            }
        });
    }
        private void updateMethod () {

            Timer timer = new Timer(1000, e -> {
                time = dateFormat.format(Calendar.getInstance().getTime());
                dateLabel.setText(time);
            });
            timer.start();
        }
}


