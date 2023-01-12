import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.ContentHandler;

public class Main extends JFrame {
    private Vis contents;
    private File f;

    public Main (){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,600);
        contents = new Vis();
        contents.getColorString("null");
        setContentPane(contents);
        var wind= creatMyBar();
        setJMenuBar(wind);
        setVisible(true);


    }
    public JMenuBar creatMyBar(){
        JMenuBar j=new JMenuBar();
        var folder= new JMenu("Choose Folder");
        var folder2 =new JMenuItem("Click to choose");
        var color =new JMenu("Color");
        var type= new JMenuItem("type");
        var date= new JMenuItem("date");
        var p= new JMenuItem("Random");
        var none=new JMenuItem("None");
        none.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                contents.getColorString("N");
                contents.getFile(f);
                contents.changeColor();
                setContentPane(contents);
            }
        });
        p.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                contents.getColorString("P");
                contents.getFile(f);
                contents.changeColor();
                setContentPane(contents);
            }
        });
        date.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contents.getFile(f);
                contents.triggerDate();
                setContentPane(contents);
            }
        });
        type.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                contents.getFile(f);
                contents.triggerType();
                setContentPane(contents);
            }
        });

        folder2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contents.getColorString(null);
                JFileChooser choose=new JFileChooser();
                choose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                choose.showOpenDialog(null);
                f=choose.getSelectedFile();
                contents.getFile(f);
                setContentPane(contents);
                setTitle(f.getAbsolutePath());
            }
        });

        folder.add(folder2);
        color.add(type);
        color.add(date);
        color.add(p);
        color.add(none);
        j.add(folder);
        j.add(color);


        return j;
    }

    public static void main (String[] args){

        new Main();
    }


}
