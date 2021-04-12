package iskola_fileread;

import java.io.RandomAccessFile;
import java.util.ArrayList;

public class ISKOLA_fileread {
    public static  int maxNameLegth=0;  //publikus változó ebben tároljuk majd el a leghosszab név hosszát
    public static ArrayList<Tanulok> tanulok=new ArrayList<>();
    
    public static void beolvas(){
        try {
            RandomAccessFile file= new RandomAccessFile("C:/Adatok/txt_filok/nevek.txt","r");
            while(file.getFilePointer()<file.length()){
                tanulok.add(new Tanulok(file.readLine()));
            }
            System.out.println(tanulok.size()+" tanuló jár az iskolába.");

            //megnézem hogy melyik tanulóknak van aleghosszab neve
            for (int i = 0; i < tanulok.size()-1; i++) {
                    if(tanulok.get(i).nameLength()>maxNameLegth){
                        //ha bármely név hosszab mint a max hosz adott értéke akkor a max hosz uj erteknek felveszi a vizsgált név hosszának a számát
                        maxNameLegth=tanulok.get(i).nameLength();
                }
               // System.out.println(tanulok.get(i).getName()+" "+tanulok.get(i).nameLength());
            }
            //megnézem az összes tanulót és hogy bármelyik neve megegyezik e a leghosszab névvel ha igen kiiratom
            for (int j = 0; j < tanulok.size()-1; j++) {
                if(tanulok.get(j).nameLength()==maxNameLegth){
                    System.out.println("A leghosszab tanuló neve "+tanulok.get(j).getName()+" nak/nek van, amely "+tanulok.get(j).nameLength()+" karaktertből áll");
               } 
            }
            //kiiratom az első és az utolsó tanuló egyedi azonosítóját
            System.out.println("Az első tanuló azonosítója: "+tanulok.get(0).getStudentId());
            System.out.println("Az utolsó tanuló azonosítója: "+tanulok.get(tanulok.size()-1).getStudentId());
            //  System.out.println(tanulok.get(i).getName()+" "+tanulok.get(i).nameLength());
              
               System.out.println("");
               file.close();
        } 
        catch (Exception e) {
               System.out.println(e);
        }
    }
    public static void main(String[] args) {
        beolvas();
    }
}
class Tanulok{
    private int birtYear;
    private char classId;
    private String name;
    private String [] studentsNames;  //a tanulok neve eltárolva
    private int nameRealLength;       //a nevek valódi hossza
    private String StudentId;         //a nevek azonosítója
    private String [] nameParts;      //a nevek egyes részei külön eltárolva
    public Tanulok(String row) {
        String [] data=row.split(";");
        this.birtYear = Integer.parseInt(data[0]);  
        this.classId =data[1].charAt(0);;
        this.name = data[2];
        studentsNames=new String[data.length]; //meghatározzuk a tomb hosszát ami mindig a felszeletelt adatok tomb hosszával lesz egyenlő
        //beállítom a tanulok neveit 
        for (int i = 0; i < studentsNames.length; i++) {
            studentsNames[i]=data[2];
        }
        //az eggyes neveket feldarabolom hogy külön tudjam őket kezelni
        nameParts=name.split(" ");
        //inicializálom az egyes diákok azonosítóját
        StudentId=((String.valueOf(birtYear).charAt(3))+(classId+nameParts[0].substring(0, 3))+(nameParts[1].substring(0, 3))).toLowerCase();
    }
//getterek------------------------
    public int getBirtYear() {
        return birtYear;
    }

    public char getClassId() {
        return classId;
    }

    public String getName() {
        return name;
    }
    public String getStudentId(){
        return StudentId;
    }
//getterek vége-----------------
    
//meghatározza a nevek hosszát szóköz nélkül
    public int nameLength(){
        for (int i = 0; i < studentsNames.length; i++) {
             int spaceNum=0;
             char [] nameLetters=studentsNames[i].toCharArray();  //a diákok neveit karakterekre bontom
             for (int j = 0; j < nameLetters.length; j++) {
               if(Character.isLetter(nameLetters[j])) { //ha karakter a vizságlt karakter akkor a ciklos folytatódik
                   continue;
                }
               else{                                   //ha nem karakter a vizságlt karakter akkor a szőközök számlálót növeljük eggyel 
                   spaceNum++;
               }
            }
            //a nev valódi hosszát inicializáljuk ami ugy jön ki hogy a nev hosszából kivonjuk a szőközök számát! 
            nameRealLength=nameLetters.length-spaceNum;
           
        }
      return nameRealLength;
      
    }
   
    
}
