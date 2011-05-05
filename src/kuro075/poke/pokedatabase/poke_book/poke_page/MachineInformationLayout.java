package kuro075.poke.pokedatabase.poke_book.poke_page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import kuro075.poke.pokedatabase.R;
import kuro075.poke.pokedatabase.data_base.poke.PokeData;
import kuro075.poke.pokedatabase.data_base.skill.HidenMachines;
import kuro075.poke.pokedatabase.data_base.skill.OldSkillMachines;
import kuro075.poke.pokedatabase.data_base.skill.SkillDataManager;
import kuro075.poke.pokedatabase.data_base.skill.SkillMachines;
import kuro075.poke.pokedatabase.util.Utility;
import android.content.Context;
import android.util.Log;
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

public class MachineInformationLayout extends FrameLayout{
	private static final String TAG="MachineInfomationLayout";
	private static final String[] LABEL_MACHINESKILL={"No","技名"/*,"威","命","タイプ","分類","PP"*/};

	//リストアダプター
	private class ListAdapter extends ArrayAdapter<ListMachineItemBean>{
		private LayoutInflater mInflater;
		private TextView mNo,mSkillName;
		
		public ListAdapter(Context context,List<ListMachineItemBean> objects){
			super(context,0,objects);
			mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
    		  if (convertView == null) {
    			  convertView = mInflater.inflate(R.layout.machine_list_row_layout, null);
    		  }
    		  final ListMachineItemBean item = this.getItem(position);
    		  if(item != null){
    			  mNo = (TextView)convertView.findViewById(R.id.text_No);
	    	      mNo.setText(String.valueOf(item.getMachineNo()));
	    	      mSkillName = (TextView)convertView.findViewById(R.id.text_name);
	    	      mSkillName.setText(item.getSkillName());
	    	      mSkillName.setTextColor(SkillDataManager.INSTANCE.getSkillData(item.getSkillName()).getType().getColor());
	    	    }
	    	    return convertView;
    	  	}
	}

	private PokeData poke;
	private LinearLayout ll;
	private ListView MachineListView,OldMachineListView;
	
	private List<SkillMachines> skill_machines=new ArrayList<SkillMachines>();
	private List<HidenMachines> hiden_machines=new ArrayList<HidenMachines>();
	private List<OldSkillMachines> old_skill_machines=new ArrayList<OldSkillMachines>();
	
	private SkillMachines.DataTypes machine_sort_index=SkillMachines.DataTypes.No;
	private OldSkillMachines.DataTypes old_sort_index=OldSkillMachines.DataTypes.No;
	
	private final OnTouchListener flick_listener;
	private final View layout;
	public MachineInformationLayout(Context context,OnTouchListener listener,PokeData poke) {
		super(context);
		// TODO Auto-generated constructor stub
		this.poke=poke;
		this.flick_listener=listener;
		LayoutInflater factory=LayoutInflater.from(this.getContext());
		layout = factory.inflate(R.layout.machine_info_layout, null);
		
		this.addView(layout);
		initLayout();//レイアウトを初期化
        initAllSkill();//わざを初期化
        setAllSkillMachine();//わざマシンをレイアウトに登録
        setAllOldSkillMachine();//旧わざマシンをレイアウトに登録
	}
	
	/**
	 * ひでんマシンをクリックした時の動作
	 * @param index
	 */
	private void clickHidenMachine(int index){
		Utility.log(TAG, "clickHidenSkill");
		//わざ図鑑に遷移
	}
	
	/**
	 * わざマシンの列ラベルをクリックした時の動作
	 * @param data_type
	 */
	private void clickMachineColNames(SkillMachines.DataTypes data_type){
    	Utility.log(TAG,"clickMachineColNames:"+data_type.toString());
    	if(data_type==machine_sort_index){
    		Utility.reverseList(skill_machines);
    		Utility.reverseList(hiden_machines);
    		Utility.popToast(getContext(),data_type.toString()+"で逆順にソート");
    	}else{
    		Collections.sort(skill_machines, data_type.getComparator());
    		Collections.sort(hiden_machines, HidenMachines.DataTypes.fromIndex(data_type.getIndex()).getComparator());
    		machine_sort_index=data_type;
    		Utility.popToast(getContext(),data_type.toString()+"でソート");
    	}
    	this.setAllSkillMachine();
	}
	
	/**
	 * わざマシンをクリックした時の動作
	 * @param index
	 */
	private void clickSkillMachine(int index){
		Utility.log(TAG, "clickSkillMachine");
		//わざ図鑑に遷移
		if(skill_machines.size()>index){
			
		}else{
			clickHidenMachine(index-skill_machines.size());
		}
	}
	/**
	 * 旧わざマシンの列ラベルをクリックした時の動作
	 * @param data_type
	 */
	private void clickOldMachineColNames(OldSkillMachines.DataTypes data_type){
    	Utility.log(TAG,"clickOldMachineColNames:"+data_type.toString());
    	if(data_type==old_sort_index){
    		Utility.reverseList(old_skill_machines);
    		Utility.popToast(getContext(),data_type.toString()+"で逆順にソート");
    	}else{
    		Collections.sort(old_skill_machines, data_type.getComparator());
    		old_sort_index=data_type;
    		Utility.popToast(getContext(),data_type.toString()+"でソート");
    	}
    	this.setAllOldSkillMachine();
	}
	
	/**
	 * 旧わざマシンをクリックした時の動作
	 * @param index
	 */
	private void clickOldSkillMachine(int index){
		//わざ図鑑に遷移
	}
	/**
	 * レイアウトを初期化
	 */
	private void initLayout(){
        ll=(LinearLayout)layout.findViewById(R.id.linearlayout);
        ll.setOnTouchListener(flick_listener);
        FrameLayout fl=(FrameLayout)layout.findViewById(R.id.layout_machine);
		MachineListView = new ListView(fl.getContext());
		fl.addView(MachineListView);
		fl=(FrameLayout)layout.findViewById(R.id.layout_oldmachine);
		OldMachineListView = new ListView(fl.getContext());
		fl.addView(OldMachineListView);
        //テーブルレイアウト
        ((TableLayout)layout.findViewById(R.id.tablelayout)).setStretchAllColumns(true);
        //わざマシン　タイトル
        StringBuilder sb=new StringBuilder();
        sb.append("わざマシン(");
        sb.append(poke.getMachine().length+poke.getHiden().length);
        sb.append("個)");
        ((TextView)layout.findViewById(R.id.text_machine)).setText(new String(sb));
        //旧わざマシン　タイトル
        sb=new StringBuilder();
        sb.append("旧わざマシン(");
        sb.append(poke.getOldMachine().length);
        sb.append("個)");
        ((TextView)layout.findViewById(R.id.text_oldmachine)).setText(new String(sb));

        //わざマシン 列ラベル clickListener登録
        ((TextView)layout.findViewById(R.id.text_machine_no)).setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					clickMachineColNames(SkillMachines.DataTypes.No);
				}
            });
        ((TextView)layout.findViewById(R.id.text_machine_name)).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				clickMachineColNames(SkillMachines.DataTypes.Name);
			}
        });
        //旧わざマシン　列ラベル clickListener登録
        ((TextView)layout.findViewById(R.id.text_oldmachine_no)).setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					clickOldMachineColNames(OldSkillMachines.DataTypes.No);
				}
            });
        ((TextView)layout.findViewById(R.id.text_oldmachine_name)).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				clickOldMachineColNames(OldSkillMachines.DataTypes.Name);
			}
        });
	}
	
	/**
	 * 全てのわざデータを初期化
	 */
	private void initAllSkill(){
    	machine_sort_index=SkillMachines.DataTypes.No;
    	old_sort_index=OldSkillMachines.DataTypes.No;
    	//わざマシン
    	skill_machines.addAll(Arrays.asList(poke.getMachine()));
    	//秘伝マシン
    	hiden_machines.addAll(Arrays.asList(poke.getHiden()));
    	//旧わざマシン
        old_skill_machines.addAll(Arrays.asList(poke.getOldMachine()));
	}
	
	/**
	 * 全てのわざマシンをリストに登録
	 */
	private void setAllSkillMachine(){
    	Utility.log(TAG,"setAllMachineSkill");
    	//わざマシン
    	List<ListMachineItemBean> list=new ArrayList<ListMachineItemBean>();
    	
    	for(SkillMachines machine:skill_machines){
    		ListMachineItemBean item=new ListMachineItemBean();
    		//マシンNo
    		item.setMachineNo(String.valueOf(machine.getNo()));
    		//技名
    		item.setSkillName(machine.toString());
    		list.add(item);
    	}
    	//秘伝マシン
    	for(HidenMachines hiden:hiden_machines){
    		ListMachineItemBean item=new ListMachineItemBean();
    		//マシンNo
    		item.setMachineNo("H"+hiden.getNo());
    		//技名
    		item.setSkillName(hiden.toString());
    		list.add(item);
    	}
    	MachineListView.setAdapter(new ListAdapter(getContext(),list));
        MachineListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
				// TODO Auto-generated method stub
				clickSkillMachine(position);
			}
		});
	}
	
	/**
	 * すべての旧わざマシンをリストに登録
	 */
	private void setAllOldSkillMachine(){
    	Log.v(TAG,"setAllMachineSkill");
    	//わざマシン
    	List<ListMachineItemBean> list=new ArrayList<ListMachineItemBean>();
    	for(OldSkillMachines old_machine:old_skill_machines){
    		ListMachineItemBean item=new ListMachineItemBean();
    		//マシンNo
    		item.setMachineNo(String.valueOf(old_machine.getNo()));
    		//技名
    		item.setSkillName(old_machine.toString());
    		list.add(item);
    	}
    	OldMachineListView.setAdapter(new ListAdapter(getContext(),list));
        OldMachineListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
				// TODO Auto-generated method stub
				clickOldSkillMachine(position);
			}
		});
    }
}
