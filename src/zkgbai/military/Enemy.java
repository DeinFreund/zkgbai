package zkgbai.military;

import com.springrts.ai.oo.AIFloat3;
import com.springrts.ai.oo.clb.Unit;
import com.springrts.ai.oo.clb.UnitDef;

public class Enemy {
	Unit unit;
	int unitID;
	AIFloat3 position;
	float threatRadius = 0;
	float value = 0;
	float danger = 0;
	float speed = 0;
	int lastSeen = 0;
	boolean visible = false;
	boolean isStatic = false;
	boolean isRadarOnly = true;
	boolean isRadarVisible = false;
	boolean identified = false;
	float maxObservedSpeed = 0;
	
	Enemy(Unit unit, float cost){
		this.unit = unit;
		this.unitID = unit.getUnitId();
		this.position = unit.getPos();
		
		UnitDef def = unit.getDef();

		if(def != null){
			updateFromUnitDef(def, cost);
		}else{
			this.value = 50;
			this.danger = 0;
			this.position = unit.getPos();
			this.isStatic = false;
		}
	}
	
	@Override
	public boolean equals(Object other){
		if (other instanceof Enemy){
			return unitID == ((Enemy) other).unitID;
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		return unitID;
	}
	
	void setLastSeen(int f){
		this.lastSeen = f;
	}
	
	void setIdentified(){
		this.identified = true;
	}
	
	boolean getIdentified(){
		return this.identified;
	}
	
	int getLastSeen(){
		return lastSeen;
	}

	public void updateFromUnitDef(UnitDef u, float cost){
		this.identified = true;
		this.value = cost;
		this.isStatic = u.getSpeed() > 0;
		
		if(u.getWeaponMounts().size() > 0){
			this.danger =  u.getPower();
			this.threatRadius = u.getMaxWeaponRange();
		}		
		this.speed = u.getSpeed()/30;
	}
	
	public void updateFromRadarDef(RadarDef rd){
		this.identified = true;
		this.value = rd.getValue();
		this.isStatic = rd.getSpeed() > 0;	
		this.speed = rd.getSpeed();	
		this.danger = rd.getDanger();
		this.threatRadius = rd.getRange();
	}
}
