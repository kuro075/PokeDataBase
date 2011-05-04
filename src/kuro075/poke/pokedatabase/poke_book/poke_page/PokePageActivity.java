package kuro075.poke.pokedatabase.poke_book.poke_page;

import kuro075.poke.pokedatabase.R;
import kuro075.poke.pokedatabase.data_base.poke.PokeData;
import kuro075.poke.pokedatabase.data_base.poke.PokeDataManager;
import kuro075.poke.pokedatabase.data_base.type.TypeDataManager.TypeData;
import kuro075.poke.pokedatabase.util.Utility;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class PokePageActivity extends Activity{
	private static final String PACKAGE="kuro075.poke.pokedatabase.poke_book.poke_page";
	private static final String TAG="PokePageActivity";
	private static final String KEY_POKE_NAME=PACKAGE+"."+TAG+".poke_name",
								KEY_DISPLAYED_INDEX=PACKAGE+"."+TAG+".displayed_index";
	
	private static final int MAX_TAB_COUNT=5;
	private static final Animation inFromLeft = AnimationHelper.inFromLeftAnimation();
	private static final Animation outToRight = AnimationHelper.outToRightAnimation();
	private static final Animation inFromRight = AnimationHelper.inFromRightAnimation();
	private static final Animation outToLeft = AnimationHelper.outToLeftAnimation();
	
	private TextView text_no,text_name,text_type1,text_type2;
	private TextView[] text_tab=new TextView[MAX_TAB_COUNT];
	//ViewFlipper
	private ViewFlipper view_flipper;
	private BasicInformationLayout basic_info_layout;//基本
	private MachineInformationLayout machine_info_layout;//技マシン
	private EggSkillInformationLayout egg_info_layout;//タマゴ技
	private LvSkillInformationLayout lv_skill_info_layout;//覚える技
	private OtherInformationLayout other_info_layout;//その他
	//フリックのための情報
	private float last_X=0,last_Y=0;
	private static final float play_X=30,play_Y=45;//フリックの遊び
	
	//表示しているポケモン
	private PokeData poke=PokeDataManager.INSTANCE.NullData;
	
	/**
	 * フリックした時の動作
	 * @author sanogenma
	 *
	 */
	private class MyFlickListener implements OnTouchListener{
		private static final String TAG="MyFlickListener";
        @Override
        public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
        	onTouchAction(event.getAction(),event.getX(),event.getY());
            return true;
        }
    }
	/**
	 * 現在のタブかどうかを返す
	 * @param tab_id
	 * @return
	 */
	private boolean isCurrentTab(int tab_id){
		return tab_id==view_flipper.getDisplayedChild();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.poke_page_layout);
		Utility.log(TAG, "onCreate");
		
		Bundle extras = getIntent().getExtras();
		if(extras != null){
			poke=PokeDataManager.INSTANCE.getPokeData(extras.getString(KEY_POKE_NAME));
		}
		
		/*================/
		/  画面上部を初期化  /
		/================*/
		initTopInfoBar();
		
		/*==============/
		/  画面中部初期化  /
		/==============*/
		final MyFlickListener flick_listener=new MyFlickListener();
		view_flipper=(ViewFlipper)findViewById(R.id.layoutswitcher);
		view_flipper.setOnTouchListener(flick_listener);
		//基本
		basic_info_layout=new BasicInformationLayout(view_flipper.getContext(),poke);
		view_flipper.addView(basic_info_layout,0);
	    //覚える技
		lv_skill_info_layout=new LvSkillInformationLayout(view_flipper.getContext(),poke);
		view_flipper.addView(lv_skill_info_layout,1);
	    //技マシン
		machine_info_layout=new MachineInformationLayout(view_flipper.getContext(),poke);
		view_flipper.addView(machine_info_layout,2);
	    //タマゴ技
		egg_info_layout=new EggSkillInformationLayout(view_flipper.getContext(),poke);
		view_flipper.addView(egg_info_layout,3);
	    //その他
		other_info_layout=new OtherInformationLayout(view_flipper.getContext(),flick_listener,poke);
		view_flipper.addView(other_info_layout,4);
		
		/*==============/
		/  画面下部初期化  /
		/==============*/
	    text_tab[0]=(TextView)findViewById(R.id.tab_basic);
	    text_tab[0].setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickTextTab(0);
			}
	    });
	    text_tab[1]=(TextView)findViewById(R.id.tab_lvskill);
	    text_tab[1].setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickTextTab(1);
			}
	    });
	    text_tab[2]=(TextView)findViewById(R.id.tab_machine);
	    text_tab[2].setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickTextTab(2);
			}
	    });
	    text_tab[3]=(TextView)findViewById(R.id.tab_eggskill);
	    text_tab[3].setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickTextTab(3);
			}
	    });
	    text_tab[4]=(TextView)findViewById(R.id.tab_etc);
	    text_tab[4].setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickTextTab(4);
			}
	    });
	    
	    setClickedTab();
	}
	/**
	 * 表示しているタブを復元
	 */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		final int prev=view_flipper.getDisplayedChild();
		final int index=savedInstanceState.getInt(KEY_DISPLAYED_INDEX);
		if(index<prev){
			for(int i=0,n=prev-index;i<n;i++){
				view_flipper.showPrevious();
			}
		}else
		if(prev<index){
			for(int i=0,n=index-prev;i<n;i++){
				view_flipper.showNext();
			}
		}
		setClickedTab();
	}

	/**
	 * 表示しているタブを保存
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putInt(KEY_DISPLAYED_INDEX, view_flipper.getDisplayedChild());
	}

	/**
	 * タブをクリックした時の動作
	 * @param index
	 */
	private void clickTextTab(int index){
		Log.v(TAG,"pushTabText");
		final int prev=view_flipper.getDisplayedChild();
		if (index<prev) {
            view_flipper.setInAnimation(null);
            view_flipper.setOutAnimation(outToRight);
            for(int i=0;i<prev-index-1;i++){
                view_flipper.showPrevious();
                view_flipper.getCurrentView().setVisibility(View.GONE);
            }
            view_flipper.setInAnimation(inFromLeft);
            view_flipper.showPrevious();
        }
        if (prev<index) {
            view_flipper.setInAnimation(null);
            view_flipper.setOutAnimation(outToLeft);
            for(int i=0;i<index-prev-1;i++){
                view_flipper.showNext();
                view_flipper.getCurrentView().setVisibility(View.GONE);
            }
            view_flipper.setInAnimation(inFromRight);
            view_flipper.setOutAnimation(outToLeft);
            view_flipper.showNext();
        }
        setClickedTab();
	}
	/**
	 * <<を押した時の動作
	 */
	private void clickPrev(){
		Utility.log(TAG, "clickPrev");
		int no=poke.getNo()-1;
		if(no==0){
			no=PokeDataManager.INSTANCE.getNum()-1;
		}
		startThisActivity(this,no);
	}
	/**
	 * >>を押した時の動作
	 */
	private void clickNext(){
		Utility.log(TAG, "clickNext");
		int no=poke.getNo()+1;
		if(no==PokeDataManager.INSTANCE.getNum()){
			no=1;
		}
		startThisActivity(this,no);
	}
	/**
	 * タイプをクリックした時の動作
	 * 検索結果アクティティーを起動
	 * @param type
	 */
	private void clickTextType(TypeData type){
		Utility.log(TAG,"clickTextType");
		//検索結果アクティビティーを起動
	}
    /**
	 * 一番上のNo.名前　タイプ　のところをセット
	 */
	private void initTopInfoBar(){
		Utility.log(TAG, "initTopInfoBar");
		//TopInfoBar
		text_no=(TextView)findViewById(R.id.text_no);
		text_no.setText(poke.getNo2String());
		text_name=(TextView)findViewById(R.id.text_pokename);
		text_name.setText(poke.getName());
		text_type1=(TextView)findViewById(R.id.text_type1);
		text_type2=(TextView)findViewById(R.id.text_type2);
		TextView tv=(TextView)findViewById(R.id.prev);
		tv.setText("<<");
		tv.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickPrev();
			}
		});
		((TextView)findViewById(R.id.next)).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickNext();
			}
		});
		
		//タイプ
		Utility.log(TAG, poke.getNo2String());
		final TypeData type1=poke.getType(0);
		text_type1.setText(type1.toString());
		text_type1.setBackgroundColor(type1.getColor());
		text_type1.setTextColor(Color.WHITE);
		text_type1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickTextType(type1);
			}
		});
		
		final TypeData type2=poke.getType(1);
		if(type2==null){
			((TextView)findViewById(R.id.text_betweentype)).setVisibility(View.INVISIBLE);
			text_type2.setVisibility(View.INVISIBLE);
		}else{
			((TextView)findViewById(R.id.text_betweentype)).setVisibility(View.VISIBLE);
			text_type2.setText(type2.toString());
			text_type2.setBackgroundColor(type2.getColor());
			text_type2.setTextColor(Color.WHITE);
			text_type2.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					clickTextType(type2);
				}
			});
		}
	}
	/**
	 * 画面をタッチした時の動作
	 * @param action
	 * @param x
	 * @param y
	 */
	private void onTouchAction(int action,float x,float y){
		//Log.v(TAG,"onTouchAction:("+x+","+y+")");
		switch (action) {
	        case MotionEvent.ACTION_DOWN:
	        	last_X = x;
	        	last_Y = y;
	            break;
	        case MotionEvent.ACTION_UP:
	        	float current_X = x;
	            float current_Y = y;
	            if(Math.abs(current_Y-last_Y)<play_Y){
	                if (last_X+play_X < current_X) {
	        			final int count=view_flipper.getChildCount();
	        			/*//現在表示されているタブが「基本」で、タブがすべてでない場合
	        			if(view_flipper.getDisplayedChild()==0 && count<MAX_TAB_COUNT){
	        				for(int i=1;i<MAX_TAB_COUNT;i++){//すべてのタブを作る
	        					addNewTab(i);
	        				}
	        			}*/
	                    view_flipper.setInAnimation(inFromLeft);
	                    view_flipper.setOutAnimation(outToRight);
	                    view_flipper.showPrevious();
	                }
	                if (last_X-play_X > current_X) {
	                	final int count=view_flipper.getChildCount();
	                	final int current=view_flipper.getDisplayedChild();
	        			/*//次のタブがまだ登録されてない場合
	        			if(count <=current+1){
	        				addNewTab(current+1);
	        			}*/
	                    view_flipper.setInAnimation(inFromRight);
	                    view_flipper.setOutAnimation(outToLeft);
	                    view_flipper.showNext();
	                }
	            }
	            setClickedTab();
	            break;
	        default:
	        	break;
	    }
	}  
    /**
	 * クリックされているタブの色を変える
	 */
	private void setClickedTab(){
		final int current=view_flipper.getDisplayedChild();
		for(int i=0;i<text_tab.length;i++){
			if(i==current){
				text_tab[i].setTextColor(Color.GRAY);
			}else{
				text_tab[i].setTextColor(Color.WHITE);
			}
		}
	}
	
	
	/**
	 * このアクティビティーをstartさせる
	 * 
	 * @param context
	 * @param poke　PokeData
	 */
	public static void startThisActivity(Context context,PokeData poke){
		Utility.log(TAG, "startThisActivity with PokeData:"+poke.getNo2String());
		startThisActivity(context,poke.toString());
	}
	/**
	 * このアクティビティーをstartさせる
	 * @param context
	 * @param name
	 */
	public static void startThisActivity(Context context,String name){
		Utility.log(TAG, "startThisActivity with PokeName:"+name);
		Intent intent = new Intent(context,PokePageActivity.class);
		intent.putExtra(KEY_POKE_NAME, name);
		context.startActivity(intent);
	}
	/**
	 * このアクティビティーをstartさせる
	 * @param context
	 * @param no
	 */
	public static void startThisActivity(Context context,int no){
		Utility.log(TAG, "startThisActivity with PokeNo");
		startThisActivity(context,PokeDataManager.INSTANCE.getPokeData(no).toString());
	}
	
}
