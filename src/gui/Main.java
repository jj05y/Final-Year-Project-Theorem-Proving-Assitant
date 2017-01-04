package gui;

import interfaces.INode;

import javax.swing.*;
import trees.*;

import java.util.Vector;

/**
 * Created by joe on 23/10/16.
 */
public class Main {
    private JPanel mainComponent;

    private JList list1;
    private JList list2;
    private JButton saveTheoremButton;


    public Main() {
        System.out.println("hi");
        System.out.println("hi");

        JList<JTextArea> list = new JList<>();



        INode goldenRule = BoolTrees.goldenRule();
        for (char c : goldenRule.toString().toCharArray()) {
            list.add(new JTextArea(c+""));
        }

        Vector<JList> listForList1 = new Vector<>();
        listForList1.add(list);

    //    list1.setListData(listForList1);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Theorem Proving Assistant");
        frame.setContentPane(new Main().mainComponent);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
