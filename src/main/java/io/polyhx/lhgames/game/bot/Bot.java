package io.polyhx.lhgames.game.bot;

import io.polyhx.lhgames.game.GameInfo;
import io.polyhx.lhgames.game.Map;
import io.polyhx.lhgames.game.Player;
import io.polyhx.lhgames.game.action.AbstractPointAction;
import io.polyhx.lhgames.game.action.ActionType;
import io.polyhx.lhgames.game.action.IAction;
import io.polyhx.lhgames.game.action.MoveAction;
import io.polyhx.lhgames.game.point.Point;
import io.polyhx.lhgames.game.tile.ResourceTile;

import java.util.List;

public class Bot extends BaseBot {
	

	
			
    public IAction getAction(Map map, Player player, List<Player> others, GameInfo info) {
        boolean full = true;
        AbstractPointAction move = createMoveAction(Point.UP);
        if(player.getCarriedResource() < player.getResourceCapacity()) {
        	full = false;
        }
    	
        if(full) {
        	
        	move = goToHouse(player);
        }else {
        	move = goToNearestMineral(player,map);
        }
    	
    	return move;
    }
    
    public AbstractPointAction goToNearestMineral(Player player, Map map) {
    	// find nearest tile
    	double distMin = 1000;
    	ResourceTile nearest = null;
    	for(ResourceTile resource : map.getResources() ) {
    		if(resource.getDistanceTo(player.getPosition()) < distMin) {
    			nearest = resource;
    			distMin = resource.getDistanceTo(player.getPosition());
    			
    		}
    		
    	}
    	
    	// if mineral a coter, alors miner
    	
    	if(distMin == 1.0) {
    		return createCollectAction(new Point(nearest.getPosition().getX() - player.getPosition().getX(),nearest.getPosition().getY() - player.getPosition().getY()));
    	}else {
    		// move
    		int distX = nearest.getPosition().getX() - player.getPosition().getX();
    		int distY = nearest.getPosition().getY() - player.getPosition().getY();
    		
    		if(distX < 0 ) {
        		return createMoveAction(Point.LEFT);
        	}
        	if(distX > 0) {
        		return createMoveAction(Point.RIGHT);
        	}
        	if(distY < 0) {
        		return createMoveAction(Point.UP);
        	}
        	if( distY > 0) {
        		return createMoveAction(Point.DOWN);
        	} else {
        		return null;
        	}
    		
    		
    	}
    	
    	
    }
    public AbstractPointAction goToHouse(Player player) {
    	
    	int deltaX = player.getHousePosition().getX() - player.getPosition().getX();
    	int deltaY = player.getHousePosition().getY() - player.getPosition().getY();
    	if(deltaX < 0 ) {
    		return createMoveAction(Point.LEFT);
    	}
    	if(deltaX > 0) {
    		return createMoveAction(Point.RIGHT);
    	}
    	if(deltaY < 0) {
    		return createMoveAction(Point.UP);
    	}
    	if( deltaX > 0) {
    		return createMoveAction(Point.DOWN);
    	} else {
    		return null;
    	}
    	
    }
    
  
    
}
