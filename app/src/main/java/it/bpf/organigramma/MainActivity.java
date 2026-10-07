package it.bpf.organigramma;

import android.app.*;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.content.*;
import android.text.*;
import android.view.*;
import android.widget.*;
import java.util.*;

public class MainActivity extends Activity {
  static class Node {
    final String id, name, role, parent;
    Node(String id, String name, String role, String parent){this.id=id;this.name=name;this.role=role;this.parent=parent;}
    public String toString(){return name + (role.isEmpty()?"":" · "+role);}
  }
  final List<Node> nodes = new ArrayList<>();
  final Map<String,Node> byId = new LinkedHashMap<>();
  final Map<String,TextView> rows = new HashMap<>();
  LinearLayout tree;
  TextView detail;
  ScrollView vScroll;
  int blue = Color.rgb(0,85,165), pale = Color.rgb(231,241,252), yellow = Color.rgb(255,224,102);

  @Override public void onCreate(Bundle b){super.onCreate(b); seed(); buildUi();}
  void add(String id,String n,String r,String p){Node x=new Node(id,n,r,p);nodes.add(x);byId.put(id,x);}
  void seed(){
    add("root","BancoPosta Fondi SGR","Organigramma 01/10/2026","");
    add("ad","Stefano Giuliani","Amministratore Delegato","root");
    add("bd","Fabrizio Ferrario","Business Development","ad");
    add("rusu","Alessandra Rusu","Business Development","bd"); add("bassi","Valentina Bassi","Business Development","bd");
    add("esg","Valeria Colombo","Referente ESG","bd"); add("pellati","Elisa Pellati","ESG","esg");
    add("sp","Sandro Vergari","Responsabile Sviluppo Prodotti","bd"); add("gatti","Daniele Gatti","Sviluppo Prodotti","sp"); add("deben","Alessandro De Benedetti","Sviluppo Prodotti","sp"); add("cireddu","Michela Cireddu","Sviluppo Prodotti","sp"); add("cresci","Davide Cresci","Sviluppo Prodotti","sp");
    add("apc","Lorenzo Careddu","Amministrazione, Pianificazione e Controllo","ad"); add("donahue","Alan Donahue","APC","apc"); add("agnini","Fedora Agnini","APC","apc"); add("deangelis","Luca De Angelis","APC","apc"); add("lettieri","Claudia Lettieri","APC","apc"); add("difiore","Franco Di Fiore","APC · distacco","apc"); add("baldari","Martina Baldari","APC","apc");
    add("ca","Paolo Delle Chiaie","Compliance e Antiriciclaggio","ad"); add("bernini","Rosa Maria Bernini","Compliance e Antiriciclaggio","ca"); add("casciaro","Federica Casciaro","Compliance e Antiriciclaggio","ca"); add("cra","Marta Paccoia","Ref. 231, qualità, anticorruzione e Compliance Risk Assessment","ca"); add("rosso","Giuseppe Rosso","Presidio 231 e Compliance Risk Assessment","cra"); add("devittoris","Maria De Vittoris","Presidio 231 e Compliance Risk Assessment","cra");
    add("pfgo","Danilo Ruggiano","Processi di Funzionamento e Governo Outsourcing","ad"); add("pell","Francesca Pelliccia","Segreteria","pfgo"); add("romeo","Roberta Romeo","Segreteria","pfgo"); add("pmo","Francesco Zappone","Referente attività di PMO","pfgo"); add("gullo","Emanuele Gullo","PMO","pmo"); add("pp","Antonella Sinapi","Referente Processi e Procedure","pfgo"); add("milani","Valentina Milani","Processi e Procedure","pp"); add("sabene","Federico Sabene","Processi e Procedure","pp"); add("pricci","Simone Pricci","Processi e Procedure · distacco","pp"); add("gov","Antonella Boccia","Referente Governo Outsourcing e BCM","pfgo"); add("raparelli","Arianna Raparelli","Governo Outsourcing e BCM","gov"); add("delucia","Annamaria De Lucia","Governo Outsourcing e BCM","gov"); add("fordellone","Vincenzo Pio Fordellone","Governo Outsourcing e BCM","gov");
    add("isgd","Massimo Molinari","Investment Strategy e Gestori Delegati","ad"); add("fund","Fabio Catalano","Ref. fund selection e gestori delegati","isgd"); add("rescia","Guido Alberto Rescia","Fund selection e gestori delegati","fund"); add("minardi","Francesca Minardi","Fund selection e gestori delegati","fund"); add("panarelli","Chiara Panarelli","Fund selection e gestori delegati","fund"); add("carone","Michele Carone","Fund selection e gestori delegati","fund"); add("istrat","Guido Mastrolilli De Angelis","Referente Investment Strategy","isgd"); add("ciobanu","Maria Ciobanu","Investment Strategy","istrat"); add("moschella","Lucia Rosa Moschella","Investment Strategy","istrat"); add("mazzanti","Giovanni Maria Emanuele Mazzanti","Investment Strategy","istrat"); add("valzano","Filippo Valzano","Investment Strategy","istrat");
    add("csg","Cosimo Pacciani","Centro Studi di Gruppo","ad"); add("bacchetti","Valerio Bacchetti","Centro Studi di Gruppo","csg"); add("carboni","Gabriele Carboni","Centro Studi di Gruppo","csg"); add("studi","Eletta Savarino","Ref. studi e analisi economiche per investimenti","csg"); add("andreozzi","Paolo Andreozzi","Studi e analisi economiche","studi"); add("loreti","Lorenzo Loreti","Studi e analisi economiche","studi"); add("trend","Costanza Chiara Graziani","Ref. studi di settore e trend di transizione","csg");
    add("alg","Dilva Maria Satariano","Affari Legali e Generali · distacco","ad"); add("domolo","Claudia Domolo","Affari Legali e Generali","alg"); add("trasv","Daniela Mazza","Referente Presidi Trasversali","alg");
    add("dg","Stefano Bellani","Direttore Generale","root");
    add("inv","Andrea Carpentieri","Investments","dg"); add("clerici","Federica Clerici","Investments","inv"); add("zuccotti","Mattia Zuccotti","Investments","inv"); add("government","Catia Geri","Referente Government","inv"); add("oliva","Antonio Oliva","Government","government"); add("iacobelli","Giorgio Iacobelli","Government","government"); add("dimaro","Sabina Di Maro","Government","government"); add("multi","Gaia Salford","Referente Multiasset","inv"); add("bonaschi","Giovanni Bonaschi","Multiasset","multi"); add("taccogna","Giuseppe Taccogna","Multiasset","multi"); add("buonamano","Marco Buonamano","Multiasset","multi"); add("teruggi","Matteo Teruggi","Multiasset","multi"); add("corp","Ugo Trenta","Responsabile Corporate","inv"); add("ivone","Vanna Ivone","Corporate","corp"); add("chiellini","Lorenzo Chiellini","Corporate","corp"); add("calderini","Federico Calderini","Corporate","corp"); add("mancini","Riccardo Mancini","Corporate","corp");
    add("gf","Laura Pascali","Gestione Finanziaria","dg"); add("spagnoletta","Vito Antonio Spagnoletta","Gestione Finanziaria","gf"); add("iserafini","Ilaria Serafini","Gestione Finanziaria","gf"); add("marani","Isabella Marani","Gestione Finanziaria","gf"); add("romano","Pietro Romano","Gestione Finanziaria","gf"); add("mammoliti","Manuel Mammoliti","Gestione Finanziaria","gf");
    add("ai","Alessandra Ferone","Alternative Investments","dg"); add("scicchitano","Alessandro Scicchitano","Alternative Investments · distacco","ai"); add("tenaglia","Mattia Tenaglia","Alternative Investments · distacco","ai");
    add("ops","Lucio Montuori","Operations","dg"); add("ta","Paolo Cerbella","Responsabile Transfer Agent","ops"); add("neroni","Simone Neroni","Transfer Agent","ta"); add("stupazzini","Giorgia Stupazzini","Transfer Agent","ta"); add("bruziches","Giulia Bruziches","Transfer Agent","ta"); add("accrogliano","Francesca Accroglianó","Transfer Agent","ta"); add("fucili","Valerio Fucili","Transfer Agent","ta"); add("cr","Roberto Lisciotto","Responsabile Controlli e Reporting","ops"); add("roefaro","Massimo Roefaro","Controlli e Reporting","cr"); add("fortuna","Raffaella Fortuna","Controlli e Reporting","cr"); add("lserafini","Luca Serafini","Controlli e Reporting","cr"); add("mo","Pierpaolo Comi","Responsabile Middle Office","ops"); add("ballerini","Raffaella Ballerini","Middle Office","mo"); add("angelelli","Diletta Angelelli","Middle Office","mo"); add("civitani","Giampiero Civitani","Middle Office","mo"); add("paradiso","Nicola Paradiso","Middle Office","mo"); add("dibari","Angela Di Bari","Middle Office","mo"); add("depascale","Stefano De Pascale","Middle Office","mo");
  }
  void buildUi(){
    LinearLayout root=new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setBackgroundColor(Color.WHITE);
    TextView title=new TextView(this); title.setText("Organigramma BPF"); title.setTextColor(Color.WHITE); title.setTextSize(22); title.setTypeface(null,Typeface.BOLD); title.setPadding(24,24,24,16); title.setBackgroundColor(blue); root.addView(title,new LinearLayout.LayoutParams(-1,-2));
    AutoCompleteTextView search=new AutoCompleteTextView(this); search.setHint("Cerca nome o cognome"); search.setTextSize(18); search.setSingleLine(); search.setPadding(24,18,24,18); search.setThreshold(1); search.setAdapter(new ArrayAdapter<Node>(this,android.R.layout.simple_dropdown_item_1line,nodes)); root.addView(search,new LinearLayout.LayoutParams(-1,-2));
    detail=new TextView(this); detail.setText("Seleziona una risorsa: l'intero percorso gerarchico sarà evidenziato."); detail.setTextSize(15); detail.setPadding(24,12,24,14); detail.setBackgroundColor(pale); root.addView(detail,new LinearLayout.LayoutParams(-1,-2));
    HorizontalScrollView h=new HorizontalScrollView(this); vScroll=new ScrollView(this); tree=new LinearLayout(this); tree.setOrientation(LinearLayout.VERTICAL); tree.setPadding(12,12,40,40); buildTree("root",0); vScroll.addView(tree); h.addView(vScroll,new HorizontalScrollView.LayoutParams(-2,-1)); root.addView(h,new LinearLayout.LayoutParams(-1,0,1));
    search.setOnItemClickListener((p,v,pos,id)->select(((Node)p.getItemAtPosition(pos)).id));
    search.setOnEditorActionListener((v,a,e)->{String q=v.getText().toString().trim().toLowerCase(Locale.ITALIAN); for(Node n:nodes) if(n.name.toLowerCase(Locale.ITALIAN).contains(q)){select(n.id);return true;} return false;});
    setContentView(root);
  }
  void buildTree(String parent,int depth){ Node n=byId.get(parent); if(n!=null) addRow(n,depth); for(Node x:nodes) if(x.parent.equals(parent)) buildTree(x.id,depth+1); }
  void addRow(Node n,int depth){
    TextView t=new TextView(this); String branch=depth==0?"":"└─ "; t.setText(repeat("   ",Math.max(0,depth-1))+branch+n.name+"\n"+repeat("   ",depth)+"   "+n.role); t.setTextSize(depth<2?17:15); t.setTextColor(depth<2?blue:Color.DKGRAY); t.setPadding(12,10,16,10); if(depth<2)t.setTypeface(null,Typeface.BOLD); t.setOnClickListener(v->select(n.id)); tree.addView(t,new LinearLayout.LayoutParams(-1,-2)); rows.put(n.id,t);
  }
  String repeat(String s,int n){StringBuilder b=new StringBuilder();while(n-->0)b.append(s);return b.toString();}
  void select(String id){
    for(TextView t:rows.values()) t.setBackgroundColor(Color.TRANSPARENT);
    Node n=byId.get(id); if(n==null)return; Set<String> path=new LinkedHashSet<>(); Node x=n; while(x!=null){path.add(x.id);x=byId.get(x.parent);} for(String p:path){TextView t=rows.get(p); if(t!=null)t.setBackgroundColor(p.equals(id)?yellow:pale);} StringBuilder b=new StringBuilder(); b.append(n.name).append("\n").append(n.role).append("\n\nPercorso: "); List<String> names=new ArrayList<>(); x=n; while(x!=null){names.add(x.name);x=byId.get(x.parent);} Collections.reverse(names); b.append(android.text.TextUtils.join("  ›  ",names)); detail.setText(b.toString()); TextView target=rows.get(id); if(target!=null)vScroll.post(()->vScroll.smoothScrollTo(0,Math.max(0,target.getTop()-200)));
  }
}
