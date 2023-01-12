
import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;


public class Node {
    public long sum;
    public List<Node> child;
    public List<Long> dateModified;
    public List<String> fileType;
    public Color c;
    public long time;
    private Map<Long, Color> m;
    public String name;
    public String path;
    public String t;
    public int NodeLeft,NodeTop,NodeRight,NodeButtom;
    public long bytes;
    public String b;


    public Node(File f, String type) {
        child=new ArrayList<>();
        dateModified=new ArrayList<>();
        fileType= new ArrayList<>();
        m=new HashMap<>();

        if (f.isFile()) {
            sum = f.length();
            time=f.lastModified();
            dateModified.add(f.lastModified());
            name=f.getName();
            int i= name.lastIndexOf('.');
            if (i > 0) {
                String extension=name.substring(i + 1);
                t=extension;

                fileType.add(extension);
            }
            path=f.getAbsolutePath();
            // caculating the size
            if(sum/1024==0){
                b=sum+" bytes";
            }
            else if(sum/1024<1024){
                bytes=(sum / 1024);
                b=bytes+" KB";
            }
            else if(sum/1024<1024*1024){
                bytes=(sum / 1024/1024);
                b=bytes+" MB";
            }
            else if(sum/1024<1024*1024*1024){

                bytes=(sum / 1024/1024/1024);
                b=bytes+" GB";
            }
            setColor2(f,type);

        } else {
            File dir = f;
            File[] directoryListing = dir.listFiles();

            if (directoryListing != null) {
                for (File childFile : directoryListing) {

                    Node grace = new Node(childFile,type);
                    if(childFile.isFile()){
                    grace.time=childFile.lastModified();
                    grace.name=childFile.getName();
                    int i= grace.name.lastIndexOf('.');
                    if (i > 0) {
                        String extension=grace.name.substring(i + 1);
                        grace.t =extension;
                        fileType.add(extension);
                    }
                    grace.path=childFile.getAbsolutePath();
                    dateModified.add(childFile.lastModified());
                   }
                    sum += grace.sum;
                    setColor2(childFile,type);
                    child.add(grace);
                }
            }

        }


    }

    public void draw(int top, int left,int width, int height, String oren, Graphics g1){

        if(child.isEmpty()){
            NodeLeft=left;
            NodeTop=top;
            NodeRight=left+width;
            NodeButtom=top+height;
            g1.fillRect(left, top,width, height);
            g1.setColor(Color.BLACK);
            g1.drawRect(left, top, width, height);
            g1.setColor(c);
        }
        else{

            if(oren.equals("H")){
                for(Node cc: child){
                    float tem2=(float)cc.sum/(float)sum;
                    double w2=Math.ceil(tem2*width);
                    g1.setColor(cc.c);
                    cc.draw(top,left,(int)w2,height,"V",g1);
                    left+=w2;
                }
            }

            else if(oren.equals("V")){

                for(Node cc: child){

                    float temp2=(float)cc.sum/(float)sum;
                    double h2=Math.ceil(temp2*height);
                    g1.setColor(cc.c);
                    cc.draw(top,left,width,(int)h2,"H",g1);
                    top+=h2;
                }
            }
        }
    }

    public List getDate(){
        return dateModified;
    }
    public List getType(){ return fileType; }

    public void setColor2(File ff, String t){
        if(t==null){
            c=Color.WHITE;
        }
        else if(t=="P"){
            int r = (int) (Math.random() * (255));
            int g = (int) (Math.random() * (255));
            int b = (int) (Math.random() * (255));
            Color randomColor = new Color(r, g, b);
            c=randomColor;
        }
        else if(t=="N"){
            c=Color.WHITE;
        }

        }
    }

