import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Alarm_Clock extends JFrame implements ActionListener, Runnable
{
    private String H, M, S;
    private final JTextField textField;
    private DateFormat dateFormat = new SimpleDateFormat("H:mm:ss");
    private int hInt;
    private int mInt;
    private int sInt;
    private int sleepTime;

    public Alarm_Clock()
    {
        setLocationRelativeTo(null);
        setTitle("Alarm-Clock");
        setLayout(new GridBagLayout());

        JPanel globalPanel = new JPanel(new GridBagLayout());
        textField = new JTextField(10);
        JButton button = new JButton("OK");

        button.addActionListener(this);

        globalPanel.add(new JLabel("Set time:"), new GBC(0, 0).setInsets(0, 0, 0, 10));
        globalPanel.add(textField, new GBC(1, 0));
        globalPanel.add(button, new GBC(0, 1, 2, 1).setAnchor(GridBagConstraints.EAST).setInsets(5, 0, 0, 0));

        add(globalPanel, new GBC(0, 0).setInsets(10));

        pack();
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                Alarm_Clock alarm_clock = new Alarm_Clock();
                alarm_clock.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                alarm_clock.setVisible(true);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        String text = textField.getText();
        if (text.split(":").length == 3)
        {
            H = text.split(":")[0];
            M = text.split(":")[1];
            S = text.split(":")[2];

            if ((M.length() == 2) && (S.length() == 2))
            {
                hInt = Integer.parseInt(H);
                mInt = Integer.parseInt(M);
                sInt = Integer.parseInt(S);

                if ((hInt <=24 && hInt >= 1) && (mInt <= 59 && mInt >= 0) && (sInt <= 59 && sInt >= 0))
                {
                    String thisTimeText = dateFormat.format(new Date());
                    int[] thisTimeIntArray = new int[3];

                    for (int i = 0; i < 3; i++)
                    {
                        thisTimeIntArray[i] = Integer.parseInt(thisTimeText.split(":")[i]);
                    }

                    int alarmTime = hInt * 60 * 60 + mInt * 60 + sInt;
                    int thisTime = thisTimeIntArray[0] * 60 * 60 + thisTimeIntArray[1] * 60 + thisTimeIntArray[2];
                    if (alarmTime < thisTime)
                    {
                        alarmTime += 24 * 60 * 60;
                    }
                    sleepTime = alarmTime - thisTime;
                    this.run();
                } else
                {
                    JOptionPane.showMessageDialog(getThis(), "Invalid time format");
                }
            } else
            {
                JOptionPane.showMessageDialog(getThis(), "Invalid time format");
            }
        } else
        {
            JOptionPane.showMessageDialog(getThis(), "Invalid time format");
        }
    }

    private Alarm_Clock getThis()
    {
        return this;
    }

    @Override
    public void run()
    {
        try
        {
            Thread.sleep(sleepTime * 1000);
            JOptionPane.showMessageDialog(getThis(), "ALARM!!!");
        } catch (InterruptedException e) {e.printStackTrace();}
    }
}
