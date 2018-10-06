package io.polyhx.lhgames.game.bot;
import java.util.Random;
import io.polyhx.lhgames.game.GameInfo;
import io.polyhx.lhgames.game.Map;
import io.polyhx.lhgames.game.Player;
import io.polyhx.lhgames.game.Upgrade;
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
	Upgrade upgradeAtt1 = Upgrade.ATTACK;

	AbstractPointAction lastMove;
	
	
			
    public IAction getAction(Map map, Player player, List<Player> others, GameInfo info) {
        boolean full = true;
        AbstractPointAction move = createMoveAction(Point.UP);
        
        if(player.getTotalResource() > 15000) {
        	if(player.getAttack() < 3) {
        		if(player.getPosition().equals(player.getHousePosition())) {
        			return createUpgradeAction(upgradeAtt1);
        		}
        	}
        }
        
        
        if(player.getCarriedResource() < player.getResourceCapacity()) {
        	full = false;
        }
    	
    		 if(full) {
    	        	
    	        	move = goToHouse(player,map);
    	        
    	        	
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
    		nextTile = map.getTileBelowOf(player.getPosition());
    	}else if(dir.equals(Point.DOWN)) {
    		nextTile = map.getTileAboveOf(player.getPosition());

    	}else if(dir.equals(Point.RIGHT)) {
    		nextTile = map.getTileRightOf(player.getPosition());
    	}else if(dir.equals(Point.LEFT)) {
    		nextTile = map.getTileLeftOf(player.getPosition());
    	}else {
    		nextTile = map.getTileAboveOf(player.getPosition());
    	}
    	// if vide -> walk
    	if(nextTile.isResource()) {
    		Random rand = new Random();
    		
    		int move = rand.nextInt(5);
    		
    		
    		if(move == 1) {
    			return createMoveAction(Point.UP);
    		}else if(move == 2) {
    			return createMoveAction(Point.DOWN);
    		}else if(move == 3) {
    			return createMoveAction(Point.RIGHT);
    		}else {
    			return createMoveAction(Point.LEFT);
    		}
    		
    	}
    	if(nextTile.isPlayer()) {
    		return createMeleeAttackAction(dir);
    	}
    	if(nextTile.isWall()) {
    		return createMeleeAttackAction(dir);
    	}
    	
    	
    	
    	
    	return createMoveAction(dir);
    }
    public AbstractPointAction goToHouse(Player player,Map map) {
    	
    	int deltaX = player.getHousePosition().getX() - player.getPosition().getX();
    	int deltaY = player.getHousePosition().getY() - player.getPosition().getY();
    	
    	if(deltaX < 0) {
    		return move(Point.LEFT,player,map);
    	}
    	if(deltaX > 0) {
    		return move(Point.RIGHT,player,map);
    	}
    	if(deltaY < 0) {
    		return move(Point.UP,player,map);
    	}
    	if( deltaY > 0) {
    		return move(Point.DOWN,player,map);
    	} else {
    		return null;
    	}
    	
    }
    
    
    public Point nearestPlayer(GameInfo gameInfo, Player player) { 
		int indiceP = 0;
		int compteur = 0;
    	for( Player i : gameInfo.getOtherPlayers()) {
    		 double distNearPlayer = Double.MAX_VALUE;
    		 
    		 //
    		 if(distNearPlayer > Math.sqrt(Math.pow(i.getPosition().getX() - player.getPosition().getX(), 2)) + 
    				 Math.pow(i.getPosition().getY() - player.getPosition().getY(), 2)){
    			indiceP = compteur; 
    		 }
    		 
    		 compteur++;
    		
    	}
    	return gameInfo.getOtherPlayers().get(indiceP).getPosition();
    }
    
    
    
    
    
  
    
}
