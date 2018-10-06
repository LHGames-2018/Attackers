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
import io.polyhx.lhgames.game.tile.Tile;
import io.polyhx.lhgames.game.tile.TileContent;

import java.util.List;

public class Bot extends BaseBot {
	

	AbstractPointAction lastMove;
	
	boolean goHouseUrgent = false;
			
    public IAction getAction(Map map, Player player, List<Player> others, GameInfo info) {
        boolean full = true;
        AbstractPointAction move = createMoveAction(Point.UP);
        if(player.getCarriedResource() < player.getResourceCapacity()) {
        	full = false;
        }
    	if(goHouseUrgent) {
    		move = goToHouse(player,map);
    		
    	}else {
    		 if(full) {
    	        	
    	        	move = goToHouse(player,map);
    	        
    	        	
    	        }else {
    	        	move = goToNearestMineral(player,map);
    	        }
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
    	
    	if(distMin <= 1.1) {
    		return createCollectAction(new Point(nearest.getPosition().getX() - player.getPosition().getX(),nearest.getPosition().getY() - player.getPosition().getY()));
    	}else {
    		// move
    		int distX = nearest.getPosition().getX() - player.getPosition().getX();
    		int distY = nearest.getPosition().getY() - player.getPosition().getY();
    		
    		if(distX < 0 ) {
        		return move(Point.LEFT,player,map);
        	}
        	if(distX > 0) {
        		return move(Point.RIGHT,player,map);
        	}
        	if(distY < 0) {
        		return move(Point.UP,player,map);
        	}
        	if( distY > 0) {
        		return move(Point.DOWN,player,map);
        	} else {
        		return null;
        	}
    		
    		
    	}
    	
    	
    }
    
    public AbstractPointAction move(Point dir,Player player,Map map) {
    	
    	//if tree -> attack
    	//if guy -> attack
    	Tile nextTile;
    	
    	if(dir.equals(Point.UP)) {
    		nextTile = map.getTileAboveOf(player.getPosition());
    	}else if(dir.equals(Point.DOWN)) {
    		nextTile = map.getTileBelowOf(player.getPosition());

    	}else if(dir.equals(Point.RIGHT)) {
    		nextTile = map.getTileRightOf(player.getPosition());
    	}else if(dir.equals(Point.LEFT)) {
    		nextTile = map.getTileLeftOf(player.getPosition());
    	}else {
    		nextTile = map.getTile(player.getPosition());
    	}
    	// if vide -> walk
    	
    	if(nextTile.isEmpty() || nextTile.isHouse()) {
    		return createMoveAction(dir);
    	} 
    	if(nextTile.isWall() || nextTile.isPlayer()) {
    		return createMeleeAttackAction(dir);
    	}
    	
    		return createMeleeAttackAction(dir);
    	
    	
    	// get the type of tile
    	
    	
    	
    	//return createMoveAction(dir);
    }
    public AbstractPointAction goToHouse(Player player,Map map) {
    	
    	int deltaX = player.getHousePosition().getX() - player.getPosition().getX();
    	int deltaY = player.getHousePosition().getY() - player.getPosition().getY();
    	if(deltaX < 0 ) {
    		if(map.getTileLeftOf(player.getPosition()).isResource()) {
    			if(deltaY > 0) {
    				return move(Point.DOWN,player,map);
    			}else {
    				return move(Point.UP,player,map);
    			}
    		}
    		return move(Point.LEFT,player,map);
    	}
    	if(deltaX > 0) {
    		if(map.getTileRightOf(player.getPosition()).isResource()) {
    			if(deltaY > 0) {
    				return move(Point.DOWN,player,map);
    			}else {
    				return move(Point.UP,player,map);
    			}
    		}
    		return move(Point.RIGHT,player,map);
    	}
    	if(deltaY < 0) {
    		if(map.getTileAboveOf(player.getPosition()).isResource()) {
    			if(deltaX < 0) {
    				return move(Point.LEFT,player,map);
    			}else {
    				return move(Point.RIGHT,player,map);
    			}
    		}
    		return move(Point.UP,player,map);
    	}
    	if( deltaY > 0) {
    		if(map.getTileBelowOf(player.getPosition()).isResource()) {
    			if(deltaX < 0) {
    				return move(Point.LEFT,player,map);
    			}else {
    				return move(Point.RIGHT,player,map);
    			}
    		}
    		return move(Point.DOWN,player,map);
    	} else {
    		return null;
    	}
    	
    }
    
    
    
  
    
}
