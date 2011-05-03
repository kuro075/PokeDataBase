package kuro075.poke.pokedatabase.data_base.skill;

import android.util.Log;


public class Test {
	private static final String TAG="SkillData.Test";
	StringBuilder sb;
	public Test(){
       	sb=new StringBuilder();
       	for(int i=0,n=SkillDataManager.INSTANCE.getNum();i<n;i++){
       		Log.v(TAG,"No."+i);
       		SkillData skill=SkillDataManager.INSTANCE.getSkillData(i);
       		sb.append(skill.getNo());
       		sb.append(" ");
       		sb.append(skill.toString());
       		sb.append("\n");
       		for(kuro075.poke.pokedatabase.data_base.skill.SkillDataManager.ViewableInformations vi:kuro075.poke.pokedatabase.data_base.skill.SkillDataManager.ViewableInformations.values()){
       			sb.append(vi.toString());
       			sb.append(":");
       			sb.append(vi.getInformation(skill));
       			sb.append("\n");
       		}
       		sb.append("\n");
       	}
	}
	
	public String getTestString(){
		return new String(sb);
	}
}
