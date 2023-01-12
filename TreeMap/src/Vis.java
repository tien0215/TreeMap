import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Vis extends JPanel implements MouseInputListener {

    private Node n;
    private File f;
    // variable tt is for different color mode
    private String tt;
    private String path;
    private Map<Long, Color> m;
    private Map<String, Color> m2;

    public Vis(){
        addMouseListener(this);
        addMouseMotionListener(this);
        m=new HashMap<>();
        m2=new HashMap<>();
    }


    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(n!=null) {
            n.draw(0, 0, getWidth(), getHeight(), "H", g);
        }
    }


    public void colorDate(Node mytime){
        List da=mytime.getDate();
        List nd=new ArrayList<Long>();

        if(mytime.child.isEmpty()) {
            for (var aa : da) {
                if (!nd.contains(aa)) {
                    nd.add(aa);
                }
            }

        // assign color to different date
            for (var a : nd) {
                int r = (int) (Math.random() * (255));
                int g = (int) (Math.random() * (255));
                int b = (int) (Math.random() * (255));
                Color randomColor = new Color(r, g, b);
                m.put((Long) a, randomColor);
            }
        }
        else{

            for(var tt: mytime.child){
                colorDate(tt);
            }
        }
    }

    // assign color to different node depends on the time
    public void assignDate(Node node){
        if(node.child.isEmpty()){
            node.c=m.get(node.time);
        }

        else{
            for(Node ch:node.child){
                assignDate(ch);
            }
        }

    }

    public void colorType(Node myn){

        if(myn.child.isEmpty()) {
            List da = myn.getType();
            List nd = new ArrayList<String>();
        // make sure there is no repeat value
            for (var aa : da) {

                if (!nd.contains(aa)) {
                    nd.add(aa);
                    System.out.println(aa);
                }
            }

            for (var a : nd) {
                int r = (int) (Math.random() * (255));
                int g = (int) (Math.random() * (255));
                int b = (int) (Math.random() * (255));
                Color randomColor = new Color(r, g, b);
                //System.out.println(a+" "+randomColor);
                m2.put((String) a, randomColor);

            }
        }
        else{
            for(var aa: myn.child){
                colorType(aa);
            }
        }

    }
    // assign type to each node depends on type
    public void assignType(Node node){

        if(node.child.isEmpty()){
            node.c=m2.get(node.t);

        }
        else{
            for(Node ch:node.child){
                ch.c=m2.get(ch.t);
                assignType(ch);
            }
        }
    }

    // get the file user choose for the tree map
    public void getFile(File passFile){
          n = new Node(passFile, tt);
          repaint();
    }
    //method for type color mode
    public void triggerType(){

        colorType(n);
        assignType(n);
        repaint();
    }
    //method for date color mode
    public void triggerDate(){

        colorDate(n);
        assignDate(n);
        repaint();

    }
    //method for setToolTip
    public void compare(int xx, int yy,Node nn){

        if(nn!=null){
            if(nn.child.isEmpty()){
                if(nn.NodeLeft<=xx && xx<=nn.NodeRight && nn.NodeTop<=yy && yy<=nn.NodeButtom){
                    setToolTipText(nn.path+" ( size: "+nn.b+ ")");
                    path=nn.path;

                }
            }
            else{
                for(Node c: nn.child){
                        compare(xx,yy,c);
                }
            }
        }
        repaint();
    }

    public void getColorString(String r){
        tt=r;
    }
    public void changeColor(){
        repaint();
    }



    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

        int x=e.getX();
        int y=e.getY();
        compare(x,y,n);
        //this is to open the file by click the node
        try {
            Desktop.getDesktop().open(new File (path));
        } catch (IOException ee) {
            ee.printStackTrace();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // when mouse hover over, the tooptip will show
        int x=e.getX();
        int y=e.getY();
        compare(x,y,n);
        repaint();
    }
}
