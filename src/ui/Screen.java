package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;

import controller.*;
import model.*;

public class Screen implements ActionListener,IObserver{
	
	private IModel model;
	private IController controller;
	
	private JTable tableJournals;
    private DefaultTableModel modelTableJournals;
    private JButton buttonJournalsNew;
    private JButton buttonJournalsAddToDistributor;
    private JTable tableSubscribers;
    private DefaultTableModel modelTableSubscribers;
    private JComboBox<SubscriberType> comboBoxSubscriberType;
    private JButton buttonSubscribersNew;
    private JButton buttonSubscribersAddToDistributor;
    private JDateChooser dateChooser;
    private JTable tableDistributorJournals;
    private DefaultTableModel modelTableDistributorJournals;
    private JTable tableDistributorSubscribers;
    private DefaultTableModel modelTableDistributorSubscribers;
    private JComboBox<ListOptions> comboBoxListOptions;
    private JButton buttonListOptions;
    private JTextField textSaveState;
    private JTextField textLoadState;
    private JButton buttonSaveState;
    private JButton buttonLoadState;
    private JButton buttonAddSubscription;
    private JButton buttonReport;
    private JTable tableSubscriptions;
    private DefaultTableModel modelTableSubscriptions;
    private JTextField textPayment;
    private JButton buttonIncreasePayment;
    
    private JFrame frameNewSubscription = new JFrame("Create New Subscription");
    private JFrame frameNewIndividual = new JFrame("Create New Individual");
    private JFrame frameNewCorporation = new JFrame("Create New Corporation");
    private JFrame frameNewJournal = new JFrame("Create New Journal");
    private JFrame frameReport = new JFrame("New Report");
    private JFrame frameReportResult = new JFrame("Report");
    private JButton buttonCreateIndividual;
    private JButton buttonCreateCorporation;
    private JButton buttonCreateJournal;
    
    private JTextPane textPane;
	
	public Screen(IModel model, IController controller) {
		this.model = model;
		this.controller = controller;
		
		model.registerObserver((IObserver)this);
	}

	public void create() {
		int height = 625;
        GridBagConstraints c = new GridBagConstraints();
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        
		JFrame frame = new JFrame("Journal Distributor");
		JPanel panel_main = new JPanel();
		
		JPanel column1 = new JPanel();
        column1.setPreferredSize(new Dimension(300,height));
        column1.setLayout(new GridBagLayout());
        
        JPanel panelJournals = new JPanel();
        panelJournals.setLayout(new GridBagLayout());
        panelJournals.setPreferredSize(new Dimension(panelJournals.getPreferredSize().width,200));
        panelJournals.setMinimumSize(new Dimension(panelJournals.getPreferredSize().width,200));
        
        modelTableJournals = new DefaultTableModel();
        modelTableJournals.addColumn("Name");
        modelTableJournals.addColumn("ISSN");
        modelTableJournals.addColumn("Frequency");
        modelTableJournals.addColumn("Price");
        tableJournals = new JTable(modelTableJournals){
            private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
                return false;
            }
            public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend)
            {
                super.changeSelection(rowIndex, columnIndex, !extend, extend);
            }
        };
        tableJournals.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                
                
            }
        });
        JScrollPane scrollPaneTableJournals = new JScrollPane(tableJournals);
        c.gridwidth = 2; c.gridx = 0; c.gridy = 0; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
        panelJournals.add(scrollPaneTableJournals,c);
        
        buttonJournalsNew = new JButton("New Journal");
        buttonJournalsNew.addActionListener(this);
        //buttonJournalsNew.setPreferredSize(new Dimension(buttonJournalsNew.getPreferredSize().width,50));
        //buttonJournalsNew.setMinimumSize(new Dimension(buttonJournalsNew.getPreferredSize().width,50));
        c.gridwidth = 1; c.gridx = 0; c.gridy = 1; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(0,2,5,2);
        panelJournals.add(buttonJournalsNew,c);
        
        buttonJournalsAddToDistributor = new JButton("Add To Distributor >>");
        buttonJournalsAddToDistributor.addActionListener(this);
        //buttonJournalsAddToDistributor.setPreferredSize(new Dimension(buttonJournalsAddToDistributor.getPreferredSize().width,50));
        //buttonJournalsAddToDistributor.setMinimumSize(new Dimension(buttonJournalsAddToDistributor.getPreferredSize().width,50));
        c.gridwidth = 1; c.gridx = 1; c.gridy = 1; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(0,2,5,2);
        panelJournals.add(buttonJournalsAddToDistributor,c);

        panelJournals.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY,1)," Journals "));
        c.gridwidth = 1; c.gridx = 0; c.gridy = 0; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(10,5,0,0);
        column1.add(panelJournals,c);
        
        
        
        JPanel panelSubscribers = new JPanel();
        panelSubscribers.setLayout(new GridBagLayout());
        panelSubscribers.setPreferredSize(new Dimension(panelSubscribers.getPreferredSize().width,200));
        panelSubscribers.setMinimumSize(new Dimension(panelSubscribers.getPreferredSize().width,200));
        
        modelTableSubscribers = new DefaultTableModel();
        modelTableSubscribers.addColumn("Name");
        modelTableSubscribers.addColumn("Address");
        tableSubscribers = new JTable(modelTableSubscribers){
            private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
                return false;
            }
            public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend)
            {
                super.changeSelection(rowIndex, columnIndex, !extend, extend);
            }
        };
        tableSubscribers.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                
                
            }
        });
        JScrollPane scrollPaneTableSubscribers = new JScrollPane(tableSubscribers);
        c.gridwidth = 2; c.gridx = 0; c.gridy = 0; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
        panelSubscribers.add(scrollPaneTableSubscribers,c);
        
        comboBoxSubscriberType = new JComboBox<>(SubscriberType.values());
        c.gridwidth = 1; c.gridx = 0; c.gridy = 1; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(0,2,5,2);
        panelSubscribers.add(comboBoxSubscriberType,c);
        buttonSubscribersNew = new JButton("New Subscriber");
        buttonSubscribersNew.addActionListener(this);
        c.gridwidth = 1; c.gridx = 1; c.gridy = 1; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(0,2,5,2);
        panelSubscribers.add(buttonSubscribersNew,c);
        
        buttonSubscribersAddToDistributor = new JButton("Add To Distributor >>");
        buttonSubscribersAddToDistributor.addActionListener(this);
        //buttonSubscribersAddToDistributor.setPreferredSize(new Dimension(buttonSubscribersAddToDistributor.getPreferredSize().width,50));
        //buttonSubscribersAddToDistributor.setMinimumSize(new Dimension(buttonSubscribersAddToDistributor.getPreferredSize().width,50));
        c.gridwidth = 2; c.gridx = 0; c.gridy = 2; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(0,2,5,2);
        panelSubscribers.add(buttonSubscribersAddToDistributor,c);

        panelSubscribers.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY,1)," Subscribers "));
        c.gridwidth = 1; c.gridx = 0; c.gridy = 1; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(10,5,0,0);
        column1.add(panelSubscribers,c);
        
        
        JPanel panelDate = new JPanel();
        panelDate.setLayout(new GridBagLayout());
        dateChooser = new JDateChooser();
        c.gridwidth = 1; c.gridx = 0; c.gridy = 0; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
        panelDate.add(dateChooser,c);
        
        JLabel labelDateInfo1 = new JLabel("* For ListAllSendingOrders choose a month and a year.");
        c.gridwidth = 1; c.gridx = 0; c.gridy = 1; c.weightx = 1.0; c.weighty = 0.0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(0,5,5,5);
        panelDate.add(labelDateInfo1,c);
        JLabel labelDateInfo2 = new JLabel("* For ListSendingOrders choose a month and a year and a journal");
        c.gridwidth = 1; c.gridx = 0; c.gridy = 2; c.weightx = 1.0; c.weighty = 0.0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(0,5,5,5);
        panelDate.add(labelDateInfo2,c);
        JLabel labelDateInfo3 = new JLabel("* For ListIncompletePayments choose a month and a year.");
        c.gridwidth = 1; c.gridx = 0; c.gridy = 3; c.weightx = 1.0; c.weighty = 0.0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(0,5,5,5);
        panelDate.add(labelDateInfo3,c);
        JLabel labelDateInfo4 = new JLabel("* For ListSubscriptions choose a journal.");
        c.gridwidth = 1; c.gridx = 0; c.gridy = 4; c.weightx = 1.0; c.weighty = 0.0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(0,5,5,5);
        panelDate.add(labelDateInfo4,c);
        JLabel labelDateInfo5 = new JLabel("* For ListSubscriptionsOfSubscriber choose a subscriber.");
        c.gridwidth = 1; c.gridx = 0; c.gridy = 5; c.weightx = 1.0; c.weighty = 0.0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(0,5,5,5);
        panelDate.add(labelDateInfo5,c);
        
        panelDate.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY,1)," Current Date "));
        c.gridwidth = 1; c.gridx = 0; c.gridy = 2; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(10,5,0,0);
        column1.add(panelDate,c);
        
        
        JPanel panelColumn1Fill = new JPanel();
        c.gridwidth = 1; c.gridx = 0; c.gridy = 10; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(10,0,0,0);
        column1.add(panelColumn1Fill,c);
        
        
        
        
        
        JPanel column2 = new JPanel();
        column2.setPreferredSize(new Dimension(300,height));
        column2.setLayout(new GridBagLayout());
        
        JPanel panelDistributorJournals = new JPanel();
        panelDistributorJournals.setLayout(new GridBagLayout());
        panelDistributorJournals.setPreferredSize(new Dimension(panelJournals.getPreferredSize().width,200));
        panelDistributorJournals.setMinimumSize(new Dimension(panelJournals.getPreferredSize().width,200));
        
        modelTableDistributorJournals = new DefaultTableModel();
        modelTableDistributorJournals.addColumn("Name");
        modelTableDistributorJournals.addColumn("ISSN");
        modelTableDistributorJournals.addColumn("Frequency");
        modelTableDistributorJournals.addColumn("Price");
        tableDistributorJournals = new JTable(modelTableDistributorJournals){
            private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
                return false;
            }
            public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend)
            {
                super.changeSelection(rowIndex, columnIndex, !extend, extend);
            }
        };
        tableDistributorJournals.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
            	if (!e.getValueIsAdjusting()) {
                    int selectedRow = tableDistributorJournals.getSelectedRow();
                    if (selectedRow >= 0) {
                        controller.journalSelected((String) tableDistributorJournals.getValueAt(selectedRow, 1));
                    } else {
                        modelTableSubscriptions.setRowCount(0);
                    }
                }
                
            }
        });
        JScrollPane scrollPaneTableDistributorJournals = new JScrollPane(tableDistributorJournals);
        c.gridwidth = 2; c.gridx = 0; c.gridy = 0; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
        panelDistributorJournals.add(scrollPaneTableDistributorJournals,c);
        

        panelDistributorJournals.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY,1)," Distributor - Journals "));
        c.gridwidth = 1; c.gridx = 0; c.gridy = 0; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(10,0,0,0);
        column2.add(panelDistributorJournals,c);
        
        
        
        JPanel panelDistributorSubscribers = new JPanel();
        panelDistributorSubscribers.setLayout(new GridBagLayout());
        panelDistributorSubscribers.setPreferredSize(new Dimension(panelDistributorSubscribers.getPreferredSize().width,200));
        panelDistributorSubscribers.setMinimumSize(new Dimension(panelDistributorSubscribers.getPreferredSize().width,200));
        
        modelTableDistributorSubscribers = new DefaultTableModel();
        modelTableDistributorSubscribers.addColumn("Name");
        modelTableDistributorSubscribers.addColumn("Address");
        tableDistributorSubscribers = new JTable(modelTableDistributorSubscribers){
            private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
                return false;
            }
            public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend)
            {
                super.changeSelection(rowIndex, columnIndex, !extend, extend);
            }
        };
        tableDistributorSubscribers.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                
                
            }
        });
        JScrollPane scrollPaneTableDistributorSubscribers = new JScrollPane(tableDistributorSubscribers);
        c.gridwidth = 2; c.gridx = 0; c.gridy = 0; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
        panelDistributorSubscribers.add(scrollPaneTableDistributorSubscribers,c);
        

        panelDistributorSubscribers.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY,1)," Distributor - Subscribers "));
        c.gridwidth = 1; c.gridx = 0; c.gridy = 1; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(10,0,0,0);
        column2.add(panelDistributorSubscribers,c);
        
        
        
        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new GridBagLayout());
        comboBoxListOptions = new JComboBox<>(ListOptions.values());
        c.gridwidth = 1; c.gridx = 0; c.gridy = 0; c.weightx = 1.0; c.weighty = 0.0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(5,5,5,2);
        panelButtons.add(comboBoxListOptions,c);
        buttonListOptions = new JButton("List");
        buttonListOptions.addActionListener(this);
        c.gridwidth = 1; c.gridx = 1; c.gridy = 0; c.weightx = 1.0; c.weighty = 0.0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(5,2,5,5);
        panelButtons.add(buttonListOptions,c);
        textSaveState = new JTextField();
        c.gridwidth = 1; c.gridx = 0; c.gridy = 1; c.weightx = 1.0; c.weighty = 0.0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(0,5,5,2);
        panelButtons.add(textSaveState,c);
        buttonSaveState = new JButton("Save State");
        buttonSaveState.addActionListener(this);
        c.gridwidth = 1; c.gridx = 1; c.gridy = 1; c.weightx = 1.0; c.weighty = 0.0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(0,2,5,5);
        panelButtons.add(buttonSaveState,c);
        textLoadState = new JTextField();
        c.gridwidth = 1; c.gridx = 0; c.gridy = 2; c.weightx = 1.0; c.weighty = 0.0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(0,5,5,2);
        panelButtons.add(textLoadState,c);
        buttonLoadState = new JButton("Load State");
        buttonLoadState.addActionListener(this);
        c.gridwidth = 1; c.gridx = 1; c.gridy = 2; c.weightx = 1.0; c.weighty = 0.0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(0,2,5,5);
        panelButtons.add(buttonLoadState,c);
        buttonAddSubscription = new JButton("Add Subscription");
        //buttonAddSubscription.setPreferredSize(new Dimension(buttonAddSubscription.getPreferredSize().width,50));
        //buttonAddSubscription.setMinimumSize(new Dimension(buttonAddSubscription.getPreferredSize().width,50));
        buttonAddSubscription.addActionListener(this);
        c.gridwidth = 2; c.gridx = 0; c.gridy = 3; c.weightx = 1.0; c.weighty = 0.0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(0,5,5,5);
        panelButtons.add(buttonAddSubscription,c);
        buttonReport = new JButton("Report");
        buttonReport.addActionListener(this);
        //buttonReport.setPreferredSize(new Dimension(buttonReport.getPreferredSize().width,50));
        //buttonReport.setMinimumSize(new Dimension(buttonReport.getPreferredSize().width,50));
        c.gridwidth = 2; c.gridx = 0; c.gridy = 4; c.weightx = 1.0; c.weighty = 0.0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(0,5,5,5);
        panelButtons.add(buttonReport,c);
        
        
        
        panelButtons.setBorder(BorderFactory.createLineBorder(Color.GRAY,1));
        c.gridwidth = 1; c.gridx = 0; c.gridy = 2; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(10,0,0,0);
        column2.add(panelButtons,c);
        
        JPanel panelColumn2Fill = new JPanel();
        c.gridwidth = 1; c.gridx = 0; c.gridy = 10; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(10,0,0,0);
        column2.add(panelColumn2Fill,c);

        
        
        JPanel column3 = new JPanel();
        column3.setPreferredSize(new Dimension(300,height));
        column3.setLayout(new GridBagLayout());
        
        JPanel panelSubscriptions = new JPanel();
        panelSubscriptions.setLayout(new GridBagLayout());
        panelSubscriptions.setPreferredSize(new Dimension(panelSubscriptions.getPreferredSize().width,200));
        panelSubscriptions.setMinimumSize(new Dimension(panelSubscriptions.getPreferredSize().width,200));
        
        modelTableSubscriptions = new DefaultTableModel();
        modelTableSubscriptions.addColumn("Subscriber");
        modelTableSubscriptions.addColumn("Journal");
        modelTableSubscriptions.addColumn("Copies");
        modelTableSubscriptions.addColumn("Payment");
        modelTableSubscriptions.addColumn("Start Date");
        modelTableSubscriptions.addColumn("Discount");
        modelTableSubscriptions.addColumn("Billing");
        tableSubscriptions = new JTable(modelTableSubscriptions){
            private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
                return false;
            }
            public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend)
            {
                super.changeSelection(rowIndex, columnIndex, !extend, extend);
            }
        };
        tableSubscriptions.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                
                
            }
        });
        JScrollPane scrollPaneTableSubscriptions = new JScrollPane(tableSubscriptions);
        c.gridwidth = 2; c.gridx = 0; c.gridy = 0; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
        panelSubscriptions.add(scrollPaneTableSubscriptions,c);
        textPayment = new JTextField();
        c.gridwidth = 1; c.gridx = 0; c.gridy = 1; c.weightx = 1.0; c.weighty = 0.0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(0,5,5,2);
        panelSubscriptions.add(textPayment,c);
        buttonIncreasePayment = new JButton("Increase Payment");
        buttonIncreasePayment.addActionListener(this);
        c.gridwidth = 1; c.gridx = 1; c.gridy = 1; c.weightx = 1.0; c.weighty = 0.0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(0,2,5,5);
        panelSubscriptions.add(buttonIncreasePayment,c);

        panelSubscriptions.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY,1)," Subscriptions "));
        c.gridwidth = 1; c.gridx = 0; c.gridy = 0; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(10,0,0,0);
        column3.add(panelSubscriptions,c);
        
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new GridBagLayout());

        textPane = new JTextPane();
        textPane.setBackground(Color.BLACK);
        textPane.setForeground(Color.WHITE);
        textPane.setEditable(false);
        JScrollPane scrollPane_logs = new JScrollPane(textPane);
        scrollPane_logs.setPreferredSize(new Dimension(scrollPane_logs.getPreferredSize().width,200));
        scrollPane_logs.setMinimumSize(new Dimension(scrollPane_logs.getPreferredSize().width,200));
        textPane.setMargin(new Insets(10,10,10,10));
        c.gridwidth = 1; c.gridx = 0; c.gridy = 0; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(5,5,5,5);
        panelInfo.add(scrollPane_logs,c);
        panelInfo.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY,1)," Information "));
        c.gridwidth = 1; c.gridx = 0; c.gridy = 1; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(10,0,0,5);
        column3.add(panelInfo,c);
        
        JPanel panelColumn3Fill = new JPanel();
        c.gridwidth = 1; c.gridx = 0; c.gridy = 10; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(10,0,0,0);
        column3.add(panelColumn3Fill,c);
        
		
		panel_main.add(column1);
		panel_main.add(column2);
		panel_main.add(column3);
		frame.add(panel_main);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(buttonSubscribersNew)) {
			if((SubscriberType)comboBoxSubscriberType.getSelectedItem() == SubscriberType.Individual && !frameNewIndividual.isVisible()) openNewIndividual();
			if((SubscriberType)comboBoxSubscriberType.getSelectedItem() == SubscriberType.Corporation && !frameNewCorporation.isVisible()) openNewCorporation();
		}
		if(e.getSource().equals(buttonJournalsNew)) {
			if(!frameNewJournal.isVisible()) openNewJournal();
		}
		if(e.getSource().equals(buttonJournalsAddToDistributor)) {
			int[] selectedRows = tableJournals.getSelectedRows();
			ArrayList<String> selectedIssns = new ArrayList<>();
			for(int i=0; i<selectedRows.length; i++) {
				selectedIssns.add((String)tableJournals.getValueAt(selectedRows[i], 1)); 
			}
			controller.addJournalsToDistributor(selectedIssns);
		}
		if(e.getSource().equals(buttonSubscribersAddToDistributor)) {
			int[] selectedRows = tableSubscribers.getSelectedRows();
			ArrayList<String> selectedNames = new ArrayList<>();
			for(int i=0; i<selectedRows.length; i++) {
				selectedNames.add((String)tableSubscribers.getValueAt(selectedRows[i], 0)); 
			}
			controller.addSubscribersToDistributor(selectedNames);
		}
		if(e.getSource().equals(buttonAddSubscription)) {
			if(!frameNewSubscription.isVisible()) openNewSubscription();
		}
		if(e.getSource().equals(buttonIncreasePayment)) {
			int selectedRow = tableSubscriptions.getSelectedRow();
			if(selectedRow < 0) {
				setInformation("Select a subscription!");
				return;
			}
			String name = (String)tableSubscriptions.getValueAt(selectedRow, 0);
			String issn = (String)tableSubscriptions.getValueAt(selectedRow, 1);
			controller.increasePayment(textPayment.getText(),name,issn);
		}
		if(e.getSource().equals(buttonLoadState)) {
			controller.loadState(textLoadState.getText());
		}
		if(e.getSource().equals(buttonSaveState)) {
			controller.saveState(textSaveState.getText());
		}
		if(e.getSource().equals(buttonListOptions)) {
			ListOptions option = (ListOptions)comboBoxListOptions.getSelectedItem();
			if(option == ListOptions.AllSendingOrders) {
				Date date = dateChooser.getDate();
				if(date == null) {
					setInformation("Select a date!");
					return;
				}
				controller.listAllSendingOrders(date);
			}
			if(option == ListOptions.SendingOrders) {
				int selectedRow = tableDistributorJournals.getSelectedRow();
				if(selectedRow < 0) {
					setInformation("Select a journal!");
					return;
				}
				Date date = dateChooser.getDate();
				if(date == null) {
					setInformation("Select a date!");
					return;
				}
				String issn = (String)tableDistributorJournals.getValueAt(selectedRow, 1);
				controller.listSendingOders(issn, date);
			}
			if(option == ListOptions.IncompletePayments) {
				Date date = dateChooser.getDate();
				if(date == null) {
					setInformation("Select a date!");
					return;
				}
				controller.listIncopletePayments(date);
			}
			if(option == ListOptions.Subscriptions) {
				int selectedRow = tableDistributorJournals.getSelectedRow();
				if(selectedRow < 0) {
					setInformation("Select a journal!");
					return;
				}
				String issn = (String)tableDistributorJournals.getValueAt(selectedRow, 1);
				controller.listSubscriptions(issn);
			}
			if(option == ListOptions.SubscriptionsOfSubscriber) {
				int selectedRow = tableDistributorSubscribers.getSelectedRow();
				if(selectedRow < 0) {
					setInformation("Select a subscriber!");
					return;
				}
				String name = (String)tableDistributorSubscribers.getValueAt(selectedRow, 0);
				controller.listSubscriptionsOfSubscriber(name);
			}
		}
		if(e.getSource().equals(buttonReport)) {
			if(!frameReport.isVisible()) openReport();
		}
		
	}
	
	public void start() {
		//textSaveState.setText("FileName");
		//textLoadState.setText("FileName");
	}
	
	public void setDate(long timeMillis) {
		dateChooser.setDate(new Date(timeMillis));
	}
	
	public void setInformation(String text) {
		textPane.setText(text);
	}
	
	public void updateJournals(Hashtable<String,Journal> journals) {
		modelTableJournals.setRowCount(0);
		for(Journal journal : journals.values()) {
			addJournalToTable(journal);
		}
		tableJournals.clearSelection();
	}
	
	private void addJournalToTable(Journal journal) {
		modelTableJournals.addRow(new Object[] {journal.getName(),journal.getIssn(),journal.getFrequency(),journal.getIssuePrice()});
	}
	
	public void updateSubscribers(Vector<Subscriber> subscribers) {
		modelTableSubscribers.setRowCount(0);
		for(Subscriber subscriber : subscribers) {
			addSubscriberToTable(subscriber);
		}
		tableSubscribers.clearSelection();
	}
	
	private void addSubscriberToTable(Subscriber subscriber) {
		modelTableSubscribers.addRow(new Object[] {subscriber.getName(),subscriber.getAddress()});
	}
	
	public void updateDistributorJournals(Hashtable<String,Journal> journals) {
		modelTableDistributorJournals.setRowCount(0);
		for(Journal journal : journals.values()) {
			addDistributorJournalToTable(journal);
		}
		tableDistributorJournals.clearSelection();
	}
	
	private void addDistributorJournalToTable(Journal journal) {
		modelTableDistributorJournals.addRow(new Object[] {journal.getName(),journal.getIssn(),journal.getFrequency(),journal.getIssuePrice()});
	}
	
	public void updateDistributorSubscribers(Vector<Subscriber> subscribers) {
		modelTableDistributorSubscribers.setRowCount(0);
		for(Subscriber subscriber : subscribers) {
			addDistributorSubscriberToTable(subscriber);
		}
		
	}
	
	private void addDistributorSubscriberToTable(Subscriber subscriber) {
		modelTableDistributorSubscribers.addRow(new Object[] {subscriber.getName(),subscriber.getAddress()});
	}
	
	public void updateSubscriptions(List<Subscription> subscriptions) {
		modelTableSubscriptions.setRowCount(0);
		for(Subscription subscription : subscriptions) {
			addSubscriptionToTable(subscription);
		}
	}
	
	private void addSubscriptionToTable(Subscription subscription) {
		String billing = "";
		if(subscription.getSubscriber() instanceof Individual) {
			Individual individual = (Individual) subscription.getSubscriber();
			billing += (individual.getExpireMonth() + "/" + individual.getExpireYear());
		}
		if(subscription.getSubscriber() instanceof Corporation) {
			Corporation corporation = (Corporation) subscription.getSubscriber();
			billing += (corporation.getIssueMonth() + "/" + corporation.getIssueYear());
		}
		modelTableSubscriptions.addRow(new Object[] {subscription.getSubscriber().getName(),subscription.getJournal().getIssn(),subscription.getCopies(),subscription.getPayment().getReceivedPayment(),subscription.getDates().getStartMonth()+"/"+subscription.getDates().getStartYear(),subscription.getPayment().getDiscountRatio(),billing});
	}
	
	public void openNewJournal() {
		frameNewJournal = new JFrame("New Journal");

		
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel labelName = new JLabel("Name");
		c.gridwidth = 1; c.gridx = 0; c.gridy = 0; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(labelName,c);
		JTextField textName = new JTextField();
		c.gridwidth = 1; c.gridx = 1; c.gridy = 0; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(textName,c);
		JLabel labelISSN = new JLabel("ISSN");
		c.gridwidth = 1; c.gridx = 0; c.gridy = 1; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(labelISSN,c);
		JTextField textISSN = new JTextField();
		c.gridwidth = 1; c.gridx = 1; c.gridy = 1; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(textISSN,c);
		JLabel labelFrequency = new JLabel("Frequency");
		c.gridwidth = 1; c.gridx = 0; c.gridy = 2; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(labelFrequency,c);
		JTextField textFrequency = new JTextField();
		c.gridwidth = 1; c.gridx = 1; c.gridy = 2; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(textFrequency,c);
		JLabel labelIssuePrice = new JLabel("IssuePrice");
		c.gridwidth = 1; c.gridx = 0; c.gridy = 3; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(labelIssuePrice,c);
		JTextField textIssuePrice = new JTextField();
		c.gridwidth = 1; c.gridx = 1; c.gridy = 3; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(textIssuePrice,c);
		buttonCreateJournal = new JButton("Create");
		buttonCreateJournal.setPreferredSize(new Dimension(400,50));
		c.gridwidth = 2; c.gridx = 0; c.gridy = 6; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(buttonCreateJournal,c);
		
		frameNewJournal.add(panel);
		frameNewJournal.pack();
		frameNewJournal.setResizable(false);
		frameNewJournal.setLocationRelativeTo(null);
		frameNewJournal.setVisible(true);
		frameNewJournal.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        buttonCreateJournal.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.addJournal(textName.getText(), textISSN.getText(), textFrequency.getText(), textIssuePrice.getText());
				
			}
        });
        
	}
	
	public void openNewIndividual() {
		frameNewIndividual = new JFrame("New Individual");

		
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel labelName = new JLabel("Name");
		c.gridwidth = 1; c.gridx = 0; c.gridy = 0; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(labelName,c);
		JTextField textName = new JTextField();
		c.gridwidth = 1; c.gridx = 1; c.gridy = 0; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(textName,c);
		JLabel labelAddress = new JLabel("Address");
		c.gridwidth = 1; c.gridx = 0; c.gridy = 1; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(labelAddress,c);
		JTextField textAddress = new JTextField();
		c.gridwidth = 1; c.gridx = 1; c.gridy = 1; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(textAddress,c);
		JLabel labelCardNumber = new JLabel("Card Number");
		c.gridwidth = 1; c.gridx = 0; c.gridy = 2; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(labelCardNumber,c);
		JTextField textCardNumber = new JTextField();
		c.gridwidth = 1; c.gridx = 1; c.gridy = 2; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(textCardNumber,c);
		JLabel labelExpireMonth = new JLabel("Expire Month");
		c.gridwidth = 1; c.gridx = 0; c.gridy = 3; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(labelExpireMonth,c);
		JMonthChooser monthChooser = new JMonthChooser();
		c.gridwidth = 1; c.gridx = 1; c.gridy = 3; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(monthChooser,c);
		JLabel labelExpireYear = new JLabel("Expire Year");
		c.gridwidth = 1; c.gridx = 0; c.gridy = 4; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(labelExpireYear,c);
		JYearChooser yearChooser = new JYearChooser();
		c.gridwidth = 1; c.gridx = 1; c.gridy = 4; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(yearChooser,c);
		JLabel labelCCV = new JLabel("CCV");
		c.gridwidth = 1; c.gridx = 0; c.gridy = 5; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(labelCCV,c);
		JTextField textCCV = new JTextField();
		c.gridwidth = 1; c.gridx = 1; c.gridy = 5; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(textCCV,c);
		buttonCreateIndividual = new JButton("Create");
		buttonCreateIndividual.setPreferredSize(new Dimension(400,50));
		c.gridwidth = 2; c.gridx = 0; c.gridy = 6; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(buttonCreateIndividual,c);
		
		frameNewIndividual.add(panel);
		frameNewIndividual.pack();
		frameNewIndividual.setResizable(false);
		frameNewIndividual.setLocationRelativeTo(null);
		frameNewIndividual.setVisible(true);
		frameNewIndividual.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        buttonCreateIndividual.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.addIndividual(textName.getText(), textAddress.getText(), textCardNumber.getText(), monthChooser.getMonth() + 1, yearChooser.getYear(), textCCV.getText());
			}
        });
	}
	
	public void openNewCorporation() {
		frameNewCorporation = new JFrame("New Corporation");

		
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel labelName = new JLabel("Name");
		c.gridwidth = 1; c.gridx = 0; c.gridy = 0; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(labelName,c);
		JTextField textName = new JTextField();
		c.gridwidth = 1; c.gridx = 1; c.gridy = 0; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(textName,c);
		JLabel labelAddress = new JLabel("Address");
		c.gridwidth = 1; c.gridx = 0; c.gridy = 1; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(labelAddress,c);
		JTextField textAddress = new JTextField();
		c.gridwidth = 1; c.gridx = 1; c.gridy = 1; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(textAddress,c);
		JLabel labelBankName = new JLabel("Bank Name");
		c.gridwidth = 1; c.gridx = 0; c.gridy = 2; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(labelBankName,c);
		JTextField textBankName = new JTextField();
		c.gridwidth = 1; c.gridx = 1; c.gridy = 2; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(textBankName,c);
		JLabel labelBankCode = new JLabel("Bank Code");
		c.gridwidth = 1; c.gridx = 0; c.gridy = 3; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(labelBankCode,c);
		JTextField textBankCode = new JTextField();
		c.gridwidth = 1; c.gridx = 1; c.gridy = 3; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(textBankCode,c);
		JLabel labelIssueDate = new JLabel("Issue Date");
		c.gridwidth = 1; c.gridx = 0; c.gridy = 4; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(labelIssueDate,c);
		JDateChooser dateChooser = new JDateChooser();
		c.gridwidth = 1; c.gridx = 1; c.gridy = 4; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(dateChooser,c);
		JLabel labelAccountNumber = new JLabel("Account Number");
		c.gridwidth = 1; c.gridx = 0; c.gridy = 7; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(labelAccountNumber,c);
		JTextField textAccountNumber = new JTextField();
		c.gridwidth = 1; c.gridx = 1; c.gridy = 7; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(textAccountNumber,c);
		buttonCreateCorporation = new JButton("Create");
		buttonCreateCorporation.setPreferredSize(new Dimension(400,50));
		c.gridwidth = 2; c.gridx = 0; c.gridy = 8; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(buttonCreateCorporation,c);
		
		frameNewCorporation.add(panel);
		frameNewCorporation.pack();
		frameNewCorporation.setResizable(false);
		frameNewCorporation.setLocationRelativeTo(null);
		frameNewCorporation.setVisible(true);
		frameNewCorporation.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        buttonCreateCorporation.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.addCorporation(textName.getText(), textAddress.getText(), textBankCode.getText(), textBankName.getText(), dateChooser.getDate(), textAccountNumber.getText());
				
			}
        });
	}
	
	public void openNewSubscription() {
		frameNewSubscription = new JFrame("New Subscription");
		
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel labelSubscriber = new JLabel("Subscriber Name");
		c.gridwidth = 1; c.gridx = 0; c.gridy = 0; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(labelSubscriber,c);
		JTextField textName = new JTextField();
		c.gridwidth = 1; c.gridx = 1; c.gridy = 0; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(textName,c);
		JLabel labelISSN = new JLabel("Journal ISSN");
		c.gridwidth = 1; c.gridx = 0; c.gridy = 1; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(labelISSN,c);
		JTextField textISSN = new JTextField();
		c.gridwidth = 1; c.gridx = 1; c.gridy = 1; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(textISSN,c);
		JLabel labelMonth = new JLabel("Start Month");
		c.gridwidth = 1; c.gridx = 0; c.gridy = 2; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(labelMonth,c);
		JMonthChooser monthChooser = new JMonthChooser();
		c.gridwidth = 1; c.gridx = 1; c.gridy = 2; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(monthChooser,c);
		JLabel labelYear = new JLabel("Start Year");
		c.gridwidth = 1; c.gridx = 0; c.gridy = 3; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(labelYear,c);
		JYearChooser yearChooser = new JYearChooser();
		c.gridwidth = 1; c.gridx = 1; c.gridy = 3; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(yearChooser,c);
		JLabel labelCopies = new JLabel("Copies");
		c.gridwidth = 1; c.gridx = 0; c.gridy = 4; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(labelCopies,c);
		JTextField textCopies = new JTextField();
		c.gridwidth = 1; c.gridx = 1; c.gridy = 4; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(textCopies,c);
		JLabel labelDiscount = new JLabel("Discount Ratio");
		c.gridwidth = 1; c.gridx = 0; c.gridy = 5; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(labelDiscount,c);
		JTextField textDiscount = new JTextField();
		c.gridwidth = 1; c.gridx = 1; c.gridy = 5; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(textDiscount,c);
		JButton buttonAddSubscription = new JButton("Create");
		buttonAddSubscription.setPreferredSize(new Dimension(400,50));
		c.gridwidth = 2; c.gridx = 0; c.gridy = 6; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,5,5,5);
		panel.add(buttonAddSubscription,c);
		
		buttonAddSubscription.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.addSubscription(textName.getText(), textISSN.getText(), monthChooser.getMonth() + 1, yearChooser.getYear(), textCopies.getText(), textDiscount.getText());
				
			}
        });
		
		frameNewSubscription.add(panel);
		frameNewSubscription.pack();
		frameNewSubscription.setResizable(false);
		frameNewSubscription.setLocationRelativeTo(null);
		frameNewSubscription.setVisible(true);
		frameNewSubscription.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void openReport() {
		frameReport = new JFrame("New Report");
		
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel labelExpireSubscriptions = new JLabel("Subscriptions which will be expired after selected date: ");
		c.gridwidth = 2; c.gridx = 0; c.gridy = 0; c.weightx = 1.0; c.weighty = 0.0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(10,5,5,5);
		panel.add(labelExpireSubscriptions,c);
		JMonthChooser monthChooser = new JMonthChooser();
		c.gridwidth = 1; c.gridx = 0; c.gridy = 1; c.weightx = 1.0; c.weighty = 0.0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(0,5,5,2);
		panel.add(monthChooser,c);
		JYearChooser yearChooser = new JYearChooser();
		c.gridwidth = 1; c.gridx = 1; c.gridy = 1; c.weightx = 1.0; c.weighty = 0.0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(0,2,5,5);
		panel.add(yearChooser,c);
		JLabel labelAnnualPayments = new JLabel("Received Annual Payments for selected year range: ");
		c.gridwidth = 2; c.gridx = 0; c.gridy = 2; c.weightx = 1.0; c.weighty = 0.0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(5,5,5,5);
		panel.add(labelAnnualPayments,c);
		JYearChooser yearChooser1 = new JYearChooser();
		c.gridwidth = 1; c.gridx = 0; c.gridy = 3; c.weightx = 1.0; c.weighty = 0.0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(0,5,5,2);
		panel.add(yearChooser1,c);
		JYearChooser yearChooser2 = new JYearChooser();
		c.gridwidth = 1; c.gridx = 1; c.gridy = 3; c.weightx = 1.0; c.weighty = 0.0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(0,2,5,5);
		panel.add(yearChooser2,c);
		JButton buttonReport = new JButton("Create Report");
		buttonReport.setPreferredSize(new Dimension(400,50));
		c.gridwidth = 2; c.gridx = 0; c.gridy = 4; c.weightx = 1.0; c.weighty = 0.0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(5,5,5,5);
		panel.add(buttonReport,c);
		
		buttonReport.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.createReport(monthChooser.getMonth()+1,yearChooser.getYear(),yearChooser1.getYear(),yearChooser2.getYear());
				
			}
        });
		
		frameReport.add(panel);
		frameReport.pack();
		frameReport.setResizable(false);
		frameReport.setLocationRelativeTo(null);
		frameReport.setVisible(true);
		frameReport.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void openReportResult(int month, int year, int year1, int year2, List<Subscription> expiredSubscriptions,List<Double> annualPayments) {
		frameReportResult = new JFrame("Report");
		
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel label1 = new JLabel("Expired Subscriptions after " + month + "/" + year);
		c.gridwidth = 1; c.gridx = 0; c.gridy = 0; c.weightx = 1.0; c.weighty = 0.0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(10,15,5,15);
        panel.add(label1,c);
		
		DefaultTableModel modelTableExpiredSubscriptions = new DefaultTableModel();
		modelTableExpiredSubscriptions.addColumn("Subscriber");
		modelTableExpiredSubscriptions.addColumn("Journal");
		modelTableExpiredSubscriptions.addColumn("Start");
		modelTableExpiredSubscriptions.addColumn("Price");
		modelTableExpiredSubscriptions.addColumn("Payment");
		modelTableExpiredSubscriptions.addColumn("Discount");
        JTable tableExpiredSubscriptions = new JTable(modelTableExpiredSubscriptions){
            private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
                return false;
            }
            public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend)
            {
                super.changeSelection(rowIndex, columnIndex, !extend, extend);
            }
        };
        JScrollPane scrollPaneTableSubscriptions = new JScrollPane(tableExpiredSubscriptions);
        scrollPaneTableSubscriptions.setPreferredSize(new Dimension(scrollPaneTableSubscriptions.getPreferredSize().width,250));
        scrollPaneTableSubscriptions.setMinimumSize(new Dimension(scrollPaneTableSubscriptions.getPreferredSize().width,250));
        c.gridwidth = 1; c.gridx = 0; c.gridy = 1; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,15,5,15);
        panel.add(scrollPaneTableSubscriptions,c);
        
        JLabel label2 = new JLabel("Received Annual Payments");
		c.gridwidth = 1; c.gridx = 0; c.gridy = 2; c.weightx = 1.0; c.weighty = 0.0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(5,15,5,15);
        panel.add(label2,c);
        
        DefaultTableModel modelTableAnnualPayments = new DefaultTableModel();
        modelTableAnnualPayments.addColumn("Year");
        modelTableAnnualPayments.addColumn("Payment");
        JTable tableAnnualPayments = new JTable(modelTableAnnualPayments){
            private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
                return false;
            }
            public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend)
            {
                super.changeSelection(rowIndex, columnIndex, !extend, extend);
            }
        };
        JScrollPane scrollPaneTablePayments = new JScrollPane(tableAnnualPayments);
        scrollPaneTablePayments.setPreferredSize(new Dimension(scrollPaneTablePayments.getPreferredSize().width,250));
        scrollPaneTablePayments.setMinimumSize(new Dimension(scrollPaneTablePayments.getPreferredSize().width,250));
        c.gridwidth = 1; c.gridx = 0; c.gridy = 3; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(5,15,15,15);
        panel.add(scrollPaneTablePayments,c);
        
        modelTableExpiredSubscriptions.setRowCount(0);
        for(Subscription subscription : expiredSubscriptions) {
        	String start = subscription.getDates().getStartMonth() + "/" + subscription.getDates().getStartYear();
        	modelTableExpiredSubscriptions.addRow(new Object[] {subscription.getSubscriber().getName(),subscription.getJournal().getIssn(),start,subscription.getJournal().getIssuePrice(),subscription.getPayment().getReceivedPayment(),subscription.getPayment().getDiscountRatio()});
        }
        modelTableAnnualPayments.setRowCount(0);
        for(int i=0;i<=year2-year1;i++) {
        	modelTableAnnualPayments.addRow(new Object[] {year1+i,annualPayments.get(i)});
        }
		
		frameReportResult.add(panel);
		frameReportResult.pack();
		frameReportResult.setResizable(false);
		frameReportResult.setLocationRelativeTo(null);
		frameReportResult.setVisible(true);
		frameReportResult.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	

	@Override
	public void notifyInfo(String message) {
		setInformation(message);
		
	}

	@Override
	public void notifySubscribers(Vector<Subscriber> subscribers) {
		updateSubscribers(subscribers);
		
	}

	@Override
	public void notifyJournals(Hashtable<String, Journal> journals) {
		updateJournals(journals);
		
	}

	@Override
	public void notifyDistributorJournals(Hashtable<String, Journal> journals) {
		updateDistributorJournals(journals);
		
	}

	@Override
	public void notifyDistributorSubscribers(Vector<Subscriber> subscribers) {
		updateDistributorSubscribers(subscribers);
		
	}

	@Override
	public void notifySubscriptions(List<Subscription> subscriptions) {
		updateSubscriptions(subscriptions);
		
	}

	@Override
	public void notifyReport(int month, int year, int year1, int year2, List<Subscription> expiredSubscriptions,
			List<Double> annualPayments) {
		if(frameReport.isVisible()) frameReport.dispose();
		openReportResult(month,year,year1,year2,expiredSubscriptions,annualPayments);
		
	}

	
	
}
