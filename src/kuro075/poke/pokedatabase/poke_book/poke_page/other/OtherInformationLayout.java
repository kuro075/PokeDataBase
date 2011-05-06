package kuro075.poke.pokedatabase.poke_book.poke_page.other;

import kuro075.poke.pokedatabase.data_base.poke.PokeData;
import kuro075.poke.pokedatabase.data_base.poke.PokeDataManager;
import kuro075.poke.pokedatabase.poke_book.poke_page.PokePageActivity;
import kuro075.poke.pokedatabase.util.Utility;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class OtherInformationLayout extends FrameLayout{
	private static final String TAG="InformationOtherOfPoke";
	private static final int TAB_ID=4;

	private PokeData poke;
	
	private TextView[] textEvolutions;
	
	private TableLayout tl_evo;
	
	public OtherInformationLayout(Context context,OnTouchListener otl,PokeData poke) {
		super(context);
		// TODO Auto-generated constructor stub
		this.poke=poke;
		
		this.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
        LinearLayout ll=new LinearLayout(this.getContext());
        ll.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setOnTouchListener(otl);
		//進化系列
		TextView tv=new TextView(ll.getContext());
        tv.setText("進化系列");
        tv.setTextColor(Color.rgb(0xFF, 0xFF, 0xCC));
        ll.addView(tv);
        tl_evo=new TableLayout(ll.getContext());
        setEvolutions();
        ll.addView(tl_evo);
        
        this.addView(ll);
    }
	
    /**
     * 進化系列の名前が押されたときの動作
     * @param pokename
     */
    private void clickEvolutionName(View v){
    	Utility.log(TAG,"clickEvolutionName");
    	for(int i=0,n=textEvolutions.length;i<n;i++){
    		if(textEvolutions[i]==v){
    			PokePageActivity.startThisActivity(getContext(), textEvolutions[i].getText().toString());
    		}
    	}
    }
    
    /**
     * すべての進化系列をセット
     */
    private void setEvolutions(){
    	final Integer[] evolutions=poke.getEvolutions();
    	textEvolutions=new TextView[evolutions.length];
    	for(int i=0,n=evolutions.length;i<n;i++){
    		TableRow tr=new TableRow(tl_evo.getContext());
    		textEvolutions[i]=new TextView(tr.getContext());
	    	textEvolutions[i].setText(PokeDataManager.INSTANCE.getPokeData(evolutions[i]).toString());
	    	if(poke.getEvolution(i).equals(poke)){
	    		textEvolutions[i].setTextColor(Color.WHITE);
	    	}else{
		    	textEvolutions[i].setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						clickEvolutionName(v);
					}
		        });
	    	}
    		tr.addView(textEvolutions[i]);
    		

    		TextView tv=new TextView(tr.getContext());
    		try {
    			int lv = Integer.parseInt(poke.getConditionEvolutions()[i]);
        		tv.setText("(Lv."+lv+")");
    		} catch (NumberFormatException e) {
    			if(poke.getConditionEvolutions()[i].equals("-")){
            		tv.setText("-");
    			}else{
    				tv.setText("("+poke.getConditionEvolutions()[i]+")");
    			}
    		}
    		tr.addView(tv);
    		tr.setPadding(0,3,0,3);
    		tl_evo.addView(tr);
    	}
    }

}
