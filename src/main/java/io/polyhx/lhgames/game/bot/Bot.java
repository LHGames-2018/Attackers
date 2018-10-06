package io.polyhx.lhgames.game.bot;

import io.polyhx.lhgames.game.GameInfo;
import io.polyhx.lhgames.game.Map;
import io.polyhx.lhgames.game.Player;
import io.polyhx.lhgames.game.action.IAction;
import io.polyhx.lhgames.game.point.Point;

import java.util.List;

public class Bot extends BaseBot {
	
	Point coordMinerais = new Point();
	boolean gotToHouse;
	boolean full = false;
	boolean goingHome = false;
	int xMovedH = 0;
	int yMovedH = 0;
	int xMoved = 0;
	
	int xMaxHome = 7;

	int xMax = 3;
	int yMax = 2;
	
			
    public IAction getAction(Map map, Player player, List<Player> others, GameInfo info) {
        // get to house
    /*	if(!gotHouse) {
    		if(xMovedH < xMaxHome) {
    			xMovedH++;
        		return createMoveAction(Point.LEFT);
    		}else {
    			gotHouse = true;
    		}
    		
    	}else {
    		// premiere etape done amene au minerais
    		if(xMoved < xMax) {
    			xMoved++;
    			return createMoveAction(Point.LEFT);
    		}else if(yMoved < yMax){
    			yMoved ++ ;
    			return createMoveAction(Point.UP);
    		}else {
    			// arrive au mineraix
    			if(player.resourceCurrent >= player.resourceCapacity) {
    				goingHome = true;
    			}else {
    				return creatCollectAction(Point.UP);
    			}
    			
    		}
    	}
    	
    	*/
    	return createMoveAction(Point.RIGHT);
    }
    
    
   // public Point nextMove() {
    	
    	
    //	Point move;
    	
    //	return move;
    	
    	
    //}
    
}
