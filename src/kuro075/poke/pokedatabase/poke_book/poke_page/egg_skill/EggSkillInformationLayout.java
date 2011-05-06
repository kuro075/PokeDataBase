package kuro075.poke.pokedatabase.poke_book.poke_page.egg_skill;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kuro075.poke.pokedatabase.R;
import kuro075.poke.pokedatabase.data_base.poke.PokeData;
import kuro075.poke.pokedatabase.data_base.skill.SkillData;
import kuro075.poke.pokedatabase.data_base.skill.SkillDataManager;
import kuro075.poke.pokedatabase.poke_book.poke_page.FlickListView;
import kuro075.poke.pokedatabase.util.IndexDataForSort;
import kuro075.poke.pokedatabase.util.Utility;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

public class EggSkillInformationLayout extends FrameLayout{
	private static final String TAG="EggSkillInformationLayout";
	public static final String[] LABEL_EGGSKILL={"技名","備考"/*,"威","命","タイプ","分類","PP"*/};
    
	//リストアダプター
	private class ListAdapter extends ArrayAdapter<ListEggSkillItemBean>{
		private LayoutInflater mInflater;
		private TextView mSkillName,mNote;
		
		public ListAdapter(Context context,List<ListEggSkillItemBean> objects){
			super(context,0,objects);
			mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		public View getView(final int position, View convertView, ViewGroup parent) {
    		  if (convertView == null) {
    			  convertView = mInflater.inflate(R.layout.egg_skill_list_row_layout, null);
    		  }
    		  final ListEggSkillItemBean item = this.getItem(position);
    		  if(item != null){
	    	      mSkillName = (TextView)convertView.findViewById(R.id.text_name);
	    	      mSkillName.setText(item.getSkillName());
	    	      mSkillName.setTextColor(SkillDataManager.INSTANCE.getSkillData(item.getSkillName()).getType().getColor());
	    	      mNote = (TextView)convertView.findViewById(R.id.text_note);
	    	      mNote.setText(item.getSkillNote());
	    	    }
	    	    return convertView;
    	  	}
	}
	
	private LinearLayout ll;
	private FlickListView EggSkillListView,TeachSkillListView;
	private final View layout;
	
	private List<SkillData> egg_skill_list,teach_skill_list;
	private List<String> egg_remarks,teach_remarks;
	private TextView textEgg,textTeach;
	int egg_sort_index,teach_sort_index;
	
	private PokeData poke;
	public EggSkillInformationLayout(Context context,OnTouchListener listener,PokeData poke) {
		super(context);
		// TODO Auto-generated constructor stub
		this.poke=poke;
		LayoutInflater factory = LayoutInflater.from(this.getContext());
		layout = factory.inflate(R.layout.egg_skill_info_layout, null);
		this.addView(layout);
		
		initLayout(listener);
        initAllSkill();
        setAllEggSkill();
        setAllTeachSkill();
	}

	/**
	 * タマゴ技の列ラベルをクリックした時の動作
	 * @param index
	 */
	private void clickEggColNames(int index){
		Utility.log(TAG,"pushEggColNames:"+index);
    	if(index==egg_sort_index){
    		Utility.reverseList(egg_skill_list);
    		Utility.reverseList(egg_remarks);
    		Utility.popToast(getContext(),"タマゴ技を"+LABEL_EGGSKILL[index]+"で逆順にソート");
    	}else{
    		if(index==0){//技名
    			List<IndexDataForSort> data_list=new ArrayList<IndexDataForSort>();
    			for(int i=0,n=egg_skill_list.size();i<n;i++){
    				data_list.add(new IndexDataForSort(i,egg_skill_list.get(i).getNo()));
    			}
    			Collections.sort(data_list);
    			for(IndexDataForSort index_data:data_list){
    				egg_skill_list.add(egg_skill_list.get(index_data.getIndex()));
    				egg_remarks.add(egg_remarks.get(index_data.getIndex()));
    			}
    			for(int i=0,n=data_list.size();i<n;i++){
    				egg_skill_list.remove(0);
    				egg_remarks.remove(0);
    			}
    		}else{//備考
    			initEggSkill();
    		}
    		Utility.popToast(getContext(),"タマゴ技を"+LABEL_EGGSKILL[index]+"でソート");
    		egg_sort_index=index;
    	}
    	this.setAllEggSkill();
	}

	/**
	 * タマゴ技をクリックした時の動作
	 * @param index
	 */
	private void clickEggSkillName(int index){
		Utility.log(TAG, "clickEggSkillName");
		if(isShown()){
			//わざ図鑑を開く（タマゴグループを送る？）
			
		}
	}
	
	/**
	 * 教え技の列ラベルをクリックした時の動作
	 * @param index
	 */
	private void clickTeachColNames(int index){
		Utility.log(TAG, "clickTeachColNames:"+index);
    	if(index==teach_sort_index){
    		Utility.reverseList(teach_skill_list);
    		Utility.reverseList(teach_remarks);
    		Utility.popToast(getContext(),"教え技を"+LABEL_EGGSKILL[index]+"で逆順にソート");
    	}else{
    		if(index==0){//技名
    			List<IndexDataForSort> data_list=new ArrayList<IndexDataForSort>();
    			for(int i=0,n=teach_skill_list.size();i<n;i++){
    				data_list.add(new IndexDataForSort(i,teach_skill_list.get(i).getNo()));
    			}
    			Collections.sort(data_list);
    			for(IndexDataForSort index_data:data_list){
    				teach_skill_list.add(teach_skill_list.get(index_data.getIndex()));
    				teach_remarks.add(teach_remarks.get(index_data.getIndex()));
    			}
    			for(int i=0,n=data_list.size();i<n;i++){
    				teach_skill_list.remove(0);
    				teach_remarks.remove(0);
    			}
    		}else{//備考
    			initTeachSkill();
    		}
    		Utility.popToast(getContext(),"教え技を"+LABEL_EGGSKILL[index]+"でソート");
    		teach_sort_index=index;
    	}
    	this.setAllTeachSkill();
	}
	
	/**
	 * 教え技をクリックした時の動作
	 * @param index
	 */
	private void clickTeachSkillName(int index){
		Utility.log(TAG, "clickTeachSkillName");
		//わざ図鑑に遷移
	}

	/**
	 * レイアウトを初期化
	 */
	private void initLayout(OnTouchListener listener){
    	Utility.log(TAG,"initLayout");
    	egg_sort_index=0;
    	teach_sort_index=0;
        ll=(LinearLayout)layout.findViewById(R.id.linearlayout);
        ll.setOnTouchListener(listener);
        FrameLayout fl=(FrameLayout)layout.findViewById(R.id.layout_eggskill);
        EggSkillListView = new FlickListView(fl.getContext(),listener);
        fl.addView(EggSkillListView);
		fl=(FrameLayout)layout.findViewById(R.id.layout_teachskill);
        TeachSkillListView = new FlickListView(fl.getContext(),listener);
        fl.addView(TeachSkillListView);
        
        //テーブルレイアウト
        ((TableLayout)layout.findViewById(R.id.tablelayout)).setStretchAllColumns(true);
        textEgg=(TextView)layout.findViewById(R.id.text_eggskill);
        textEgg.setTextColor(Color.rgb(0xFF, 0xFF, 0xCC));
        
        textTeach=(TextView)layout.findViewById(R.id.text_teachskill);
        StringBuilder sb=new StringBuilder();
        sb.append("教え技(");
        sb.append(poke.getTeachSkillBW().length+poke.getTeachSkillHS().length+poke.getTeachSkillPt().length);
        sb.append("個)");
        textTeach.setText(new String(sb));
        textTeach.setTextColor(Color.rgb(0xFF, 0xFF, 0xCC));
        

        //-----------------------------------------タマゴ技----------------------------------------------------
        //テーブルレイアウトの上の列名部分
        //タマゴ技
        ((TextView)layout.findViewById(R.id.text_eggskill_name)).setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					clickEggColNames(0);
				}
            });
        ((TextView)layout.findViewById(R.id.text_eggskill_note)).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				clickEggColNames(1);
			}
        });
        //教え技
        ((TextView)layout.findViewById(R.id.text_teachskill_name)).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				clickTeachColNames(0);
			}
        });
        ((TextView)layout.findViewById(R.id.text_teachskill_note)).setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View arg0) {
        		// TODO Auto-generated method stub
        		clickTeachColNames(1);
        	}
        });
	}
	
	/**
	 * タマゴ技を初期化
	 */
	private void initEggSkill(){
		Utility.log(TAG, "initEggSkill");
		egg_skill_list=new ArrayList<SkillData>();
		egg_remarks=new ArrayList<String>();
		/*=========/
		/  タマゴ技  /
		/=========*/
		for(int i=0,n=poke.getPositionOfEvolution();i<=n;i++){
			PokeData poke_data=poke.getEvolution(i);
			SkillData[] egg_skills=poke_data.getEggSkill();
			for(SkillData skill:egg_skills){
				if(egg_skill_list.indexOf(skill)<0){
					egg_skill_list.add(skill);
					if(poke.equals(poke_data)){
						egg_remarks.add("");
					}else{
						egg_remarks.add(poke_data.toString());
					}
				}
			}
		}
		StringBuilder sb = new StringBuilder();
		sb.append("タマゴ技(");
		sb.append(egg_skill_list.size());
		sb.append("個)");
        textEgg.setText(new String(sb));
	}
	/**
	 * 教え技を初期か
	 */
	private void initTeachSkill(){
		Utility.log(TAG, "initTeachSkill");
		teach_skill_list=new ArrayList<SkillData>();
		teach_remarks=new ArrayList<String>();
		/*========/
        /  教え技  /
        /========*/
        final String Pt="Pt";
        for(SkillData skill:poke.getTeachSkillPt()){
        	teach_skill_list.add(skill);
        	teach_remarks.add(Pt);
        }
        final String HS="HS";
        for(SkillData skill:poke.getTeachSkillHS()){
        	teach_skill_list.add(skill);
        	teach_remarks.add(HS);
        }
        final String BW="BW";
        for(SkillData skill:poke.getTeachSkillBW()){
        	teach_skill_list.add(skill);
        	teach_remarks.add(BW);
        }
	}
	/**
	 * 全ての技を初期化
	 */
	private void initAllSkill(){
		Utility.log(TAG, "initAllSkill");
		initEggSkill();
        initTeachSkill();
	}
	
	/**
	 * タマゴ技をリストに登録
	 */
	private void setAllEggSkill(){
		Utility.log(TAG, "setAllEggSKill");
		List<ListEggSkillItemBean> list=new ArrayList<ListEggSkillItemBean>();
		for(int i=0,n=egg_skill_list.size();i<n;i++){
			ListEggSkillItemBean item=new ListEggSkillItemBean();
			item.setSkillName(egg_skill_list.get(i).toString());
			item.setSkillNote(egg_remarks.get(i));
			list.add(item);
		}
		EggSkillListView.setAdapter(new ListAdapter(getContext(),list));
        EggSkillListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
				// TODO Auto-generated method stub
				clickEggSkillName(position);
			}
		});
	}
	
	/**
	 * 教え技をリストに登録
	 */
	private void setAllTeachSkill(){
		Utility.log(TAG, "setAllTeachSkill");
		List<ListEggSkillItemBean> list=new ArrayList<ListEggSkillItemBean>();
		for(int i=0,n=teach_skill_list.size();i<n;i++){
			ListEggSkillItemBean item=new ListEggSkillItemBean();
			item.setSkillName(teach_skill_list.get(i).toString());
			item.setSkillNote(teach_remarks.get(i));
			list.add(item);
		}
		TeachSkillListView.setAdapter(new ListAdapter(getContext(),list));
        TeachSkillListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
				// TODO Auto-generated method stub
				clickTeachSkillName(position);
			}
		});
	}
}
