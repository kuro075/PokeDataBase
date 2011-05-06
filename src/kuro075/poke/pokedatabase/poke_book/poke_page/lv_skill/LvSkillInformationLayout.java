package kuro075.poke.pokedatabase.poke_book.poke_page.lv_skill;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import kuro075.poke.pokedatabase.R;
import kuro075.poke.pokedatabase.data_base.poke.PokeData;
import kuro075.poke.pokedatabase.data_base.poke.PokeDataManager;
import kuro075.poke.pokedatabase.data_base.skill.SkillData;
import kuro075.poke.pokedatabase.data_base.skill.SkillDataManager;
import kuro075.poke.pokedatabase.poke_book.poke_page.FlickListView;
import kuro075.poke.pokedatabase.util.IndexDataForSort;
import kuro075.poke.pokedatabase.util.Utility;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * 覚える技
 * @author sanogenma
 *
 */
public class LvSkillInformationLayout extends FrameLayout{
	private static final String TAG="LvSkillInfomationLayout";
	private static final int[] col_index={R.id.col_DP,R.id.col_Pt,R.id.col_HS,R.id.col_BW,R.id.col_name,R.id.col_note};
	private static final String[] LABEL_LVSKILL={"DP","Pt","HS","BW","技名","備考"/*,"威","命","タイプ",,"分類","PP"*/};

	//リストアダプター
	private class ListAdapter extends ArrayAdapter<ListLvSkillItemBean>{
		private LayoutInflater mInflater;
		private TextView mDP,mPt,mHS,mBW,mName,mNote;
		
		public ListAdapter(Context context,List<ListLvSkillItemBean> objects){
			super(context,0,objects);
			mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		public View getView(final int position, View convertView, ViewGroup parent) {
    		  if (convertView == null) {
    			  convertView = mInflater.inflate(R.layout.lv_skill_list_row_layout, null);
    		  }
    		  final ListLvSkillItemBean item = this.getItem(position);
    		  if(item != null){
    			  mDP = (TextView)convertView.findViewById(R.id.text_DP);
	    	      mDP.setText(String.valueOf(item.getDP()));
    			  mPt = (TextView)convertView.findViewById(R.id.text_Pt);
	    	      mPt.setText(String.valueOf(item.getPt()));
    			  mHS = (TextView)convertView.findViewById(R.id.text_HS);
	    	      mHS.setText(String.valueOf(item.getHS()));
    			  mBW = (TextView)convertView.findViewById(R.id.text_BW);
	    	      mBW.setText(String.valueOf(item.getBW()));
	    	      mName = (TextView)convertView.findViewById(R.id.text_name);
	    	      mName.setText(item.getSkillName());
	    	      mName.setTextColor(SkillDataManager.INSTANCE.getSkillData(item.getSkillName()).getType().getColor());
    			  mNote = (TextView)convertView.findViewById(R.id.text_note);
	    	      mNote.setText(String.valueOf(item.getNote()));
	    	    }
	    	    return convertView;
    	  	}
	}
	
	private PokeData poke;

	private final View layout;
	private LinearLayout ll;
	private ToggleButton[] tb;//進化系列トグルボタン
	private FlickListView list_view;
	//"DP","Pt","HS","BW","技","威","命","タイプ","分類","PP"
	private List<SkillData> SkillList=null;
	private List<Integer[]> SkillLvsList=null;
	private List<String> SkillNote=null;
	private int sort_index=5;
	
	
	public LvSkillInformationLayout(Context context,OnTouchListener listener,PokeData poke) {
		super(context);
		// TODO Auto-generated constructor stub
		this.poke=poke;
		LayoutInflater factory=LayoutInflater.from(this.getContext());
		layout = factory.inflate(R.layout.lv_skill_info_layout, null);
		
		this.addView(layout);
		
		initAllSkill();
        ll=(LinearLayout)layout.findViewById(R.id.linearlayout);
        ll.setOnTouchListener(listener);
        
        //進化系トグルボタン
        LinearLayout ll_tmp=(LinearLayout)layout.findViewById(R.id.layout_evolution);
        if(poke.hasForm()){//フォルムチェンジがあって、自分の次の進化系だけがフォルムのときの処理
        	tb=new ToggleButton[poke.getEvolutions().length];
        }else{
        	tb=new ToggleButton[poke.getPositionOfEvolution()];
        }
        int evoindex=0;
        for(int i=0,n=tb.length;i<n;i++,evoindex++){
        	tb[i]=new ToggleButton(ll_tmp.getContext());
        	if(evoindex==poke.getPositionOfEvolution()){
        		evoindex++;
        	}
        	tb[i].setTextOn(poke.getEvolution(evoindex).toString());
        	tb[i].setTextOff(poke.getEvolution(evoindex).toString());
        	tb[i].setChecked(false);
        	tb[i].setOnCheckedChangeListener(new OnCheckedChangeListener(){
				@Override
				public void onCheckedChanged(CompoundButton button, boolean flag) {
					// TODO Auto-generated method stub
					clickEvolutionButton(button.getText().toString(),flag);
				}
        	});
        	ll_tmp.addView(tb[i]);
        }
        //カラムネーム行
        for(int i=0;i<col_index.length;i++){
        	final int index=i;
        	((TextView)layout.findViewById(col_index[i])).setOnClickListener(new OnClickListener(){
    			@Override
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				clickColNames(index);
    			}
    		});
        }
            
        list_view =new FlickListView(ll.getContext(),listener);// (ListView)layout.findViewById(R.id.listview);
        ll.addView(list_view);
        setAllLvSkill();
	}
	
	/**
     * pokenameが持っている技を重複なしで追加
     * @param pokename
     */
    private void addSkill(PokeData poke){
    	Log.v(TAG,"addSkill");
    	final SkillData[] skills=poke.getLvSkill();
    	if(skills.length>0){
	    	final Integer[][] lvs=poke.getLearningLv();
	    	final int prevlength=SkillList.size();
	    	//技を重複なし追加
	    	for(int i=0,n=skills.length;i<n;i++){
		    	if(SkillList.indexOf(skills[i])<0){
		    		SkillList.add(skills[i]);
		    		SkillLvsList.add(lvs[i]);
		    		SkillNote.add(poke.toString());
		    	}
	    	}
    	}
    }
    /**
     * データを初期化
     */
    private void initAllSkill(){
    	SkillList=new ArrayList<SkillData>();
    	SkillLvsList=new ArrayList<Integer[]>();
    	SkillNote=new ArrayList<String>();
    	SkillList.addAll(Arrays.asList(poke.getLvSkill()));
    	SkillLvsList.addAll(Arrays.asList(poke.getLearningLv()));
    	for(int i=0,n=SkillList.size();i<n;i++){
    		SkillNote.add("");
    	}
    }
    /**
     * 進化系トグルボタンが押された時の動作
     * @param poke_name
     * @param flag
     */
	private void clickEvolutionButton(String poke_name,boolean flag){
    	Utility.log(TAG,"pushEvolutionButton");
    	final int prev_num=SkillList.size();
    	initAllSkill();
    	//Onになっているトグルボタンのポケモンのわざを全て登録
		for(int i=0;i<tb.length;i++){
			if(tb[i].isChecked()){
				addSkill(PokeDataManager.INSTANCE.getPokeData(tb[i].getText().toString()));
			}
		}
    	sortSkill(sort_index);//ソートする
    	setAllLvSkill();
    	StringBuilder sb=new StringBuilder();
    	if(flag){
    		sb.append(poke_name);
    		sb.append("の時に覚える技を追加しました(");
    		sb.append(SkillList.size()-prev_num);
    		sb.append("個)");
    		Utility.popToast(this.getContext(),new String(sb));
    	}else{
    		sb.append(poke_name);
    		sb.append("の時に覚える技を削除しました(");
    		sb.append(prev_num-SkillList.size());
    		sb.append("個)");
    		Utility.popToast(this.getContext(),new String(sb));
    	}
	}
	
	private void clickColNames(int operate){
    	Log.v(TAG,"pushColNames");
    	if(operate==sort_index){//すでにソートしてある場合
    		reverseAllSkill();
    		if(operate<4){
    			Utility.popToast(this.getContext(),LABEL_LVSKILL[operate]+"のLvで逆順にソートしました。");
    		}else if(operate>4){
    			Utility.popToast(this.getContext(),LABEL_LVSKILL[operate]+"で逆順にソートしました。");
    		}
    	}else{
    		this.sortSkill(operate);
    		if(operate>=4){
    			Utility.popToast(this.getContext(),LABEL_LVSKILL[operate]+"でソートしました。");
    		}else{
    			Utility.popToast(this.getContext(),LABEL_LVSKILL[operate]+"のLvでソートしました。");
    		}
    	}
    	setAllLvSkill();
	}
	/**
	 * 技をクリックした時の動作
	 * @param position
	 */
    private void clickSkillName(int position){
    	Utility.log(TAG, "clickLvSkill");
    	if(isShown()){
        	//わざ図鑑を呼び出す
    		
    	}
    }
    /**
     * データをすべて逆順にする
     */
    private void reverseAllSkill(){
    	for(int i=SkillList.size()-2;i>=0;i--){
    		SkillList.add(SkillList.get(i));
    		SkillList.remove(i);
    		SkillLvsList.add(SkillLvsList.get(i));
    		SkillLvsList.remove(i);
    		SkillNote.add(SkillNote.get(i));
    		SkillNote.remove(i);
    	}
    }
    /**
     * すべての覚える技をセット
     */
    private void setAllLvSkill(){
    	Log.v(TAG,"setAllLvSkill");
        List<ListLvSkillItemBean> list = new ArrayList<ListLvSkillItemBean>();
    	for(int i=0,n=SkillList.size();i<n;i++){
    		ListLvSkillItemBean item=new ListLvSkillItemBean();
    		final Integer[] lv=SkillLvsList.get(i);
    		//習得LV
    		if(lv[0]<0){
    			item.setDP("-");
    		}else{
    			item.setDP(String.valueOf(lv[0]));
    		}
    		if(SkillLvsList.get(i)[1]<0){
    			item.setPt("-");
    		}else{
    			item.setPt(String.valueOf(lv[1]));
    		}
    		if(lv[2]<0){
    			item.setHS("-");
    		}else{
    			item.setHS(String.valueOf(lv[2]));
    		}
    		if(lv[3]<0){
    			item.setBW("-");
    		}else{
    			item.setBW(String.valueOf(lv[3]));
    		}
    		//技名
    		item.setSkillName(SkillList.get(i).toString());
    		
    		//備考
    		item.setNote(SkillNote.get(i));
    		list.add(item);
    	}
        list_view.setAdapter(new ListAdapter(getContext(),list));
        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
				// TODO Auto-generated method stub
				clickSkillName(position);
			}
		});
        StringBuilder sb=new StringBuilder();
        sb.append("レベルアップで覚える技(");
        sb.append(SkillList.size());
        sb.append("個)");
        ((TextView)findViewById(R.id.text_lv_skill)).setText(new String(sb));
    }
    /**
     * 技順をソート
     * @param index
     */
    private void sortSkill(int operate){
    	//備考の場合
    	if(operate<0 || 4<operate){
			initAllSkill();
			for(int i=0;i<tb.length;i++){
				if(tb[i].isChecked()){
					addSkill(PokeDataManager.INSTANCE.getPokeData(tb[i].getText().toString()));
				}
			}
			sort_index=operate;
			return;
    	}
    	
    	//ソート用のデータ作成
    	List<IndexDataForSort> data_list=new ArrayList<IndexDataForSort>();
    	if(0<=operate && operate<4){//DP,Pt,HS,BW
    		int reigai=1000;
    		for(int i=0,n=SkillList.size();i<n;i++){
    			int tmp=SkillLvsList.get(i)[operate];
    			if(tmp<0){
    				tmp=reigai++;
    			}
    			data_list.add(new IndexDataForSort(i,tmp));
    		}
    	}else{
    		for(int i=0,n=SkillList.size();i<n;i++){
    			data_list.add(new IndexDataForSort(i,SkillList.get(i).getNo()));
    		}
    	}
    	Collections.sort(data_list);
    	for(IndexDataForSort index:data_list){
	    	SkillList.add(SkillList.get(index.getIndex()));
	    	SkillLvsList.add(SkillLvsList.get(index.getIndex()));
	    	SkillNote.add(SkillNote.get(index.getIndex()));
    	}
    	for(int i=0,n=data_list.size();i<n;i++){
    		SkillList.remove(0);
    		SkillLvsList.remove(0);
    		SkillNote.remove(0);
    	}
		sort_index=operate;//ソートしたインデックスナンバーを保存
    }
}
