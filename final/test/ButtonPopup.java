import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ButtonPopup {

    public static void main(String[] args) {
        new ButtonPopup();
    }

    public ButtonPopup() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }

                JFrame frame = new JFrame("Testing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new TestPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public class TestPane extends JPanel {

        public TestPane() {
            setLayout(new GridBagLayout());

            final JPopupMenu popup = new JPopupMenu();
            DefaultListModel<String> model = new DefaultListModel<>();
            model.addElement("Item 1");
            model.addElement("Item 2");
            model.addElement("Item 3");
            model.addElement("Item 4");
            model.addElement("Item 5");
            JList list = new JList(model);
            popup.setLayout(new BorderLayout());
            popup.add(new JScrollPane(list));

            final JButton button = new JButton("Pop");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Dimension size = popup.getPreferredSize();
                    int x = (button.getWidth() - size.width) / 2;
                    int y = button.getHeight();
                    popup.show(button, x, y);
                }
            });

            list.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    popup.setVisible(false);
                }
            });

            add(button);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(200, 200);
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.dispose();
        }        
    }    
}