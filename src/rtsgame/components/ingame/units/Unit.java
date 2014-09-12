/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsgame.components.ingame.units;

import java.util.ArrayList;
import java.util.Scanner;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.SpriteSheet;
import rtsgame.MyMath;
import rtsgame.components.Button;
import rtsgame.components.ingame.Camera;
import rtsgame.components.ingame.Player;
import rtsgame.components.ingame.vfx.ShotVFX;
import rtsgame.resources.Sounds;
import rtsgame.states.BaseGameState;

/**
 *
 * @author GeoYS_2
 */
public class Unit {
    public static final int UNIT = 1;
    public static final int BUILDING = 2;
    public static final int TANK = 3;
    public static final int NEST = 4;
    public static final int ENGINEER = 76;
    public static final int SCOUT = 72;
    public static final int SNIPER = 34;
    public static final int GRENADIER = 22;
    public static final int NONCOLLIDEABLE = 75647;
    public static final int GAIA = 123142;
    public static final int WALL = 156743;
    public static final int UNGROUPED = 123213;
    public static final int COLLISIONBUFFER = 4;
    public static Image walkingSpriteImage, deadUnit, attackingUnit;
    
    protected Vector2f lastLoc2, lastLoc, location, lastTarget;
    protected ArrayList<Vector2f> targets;
    protected boolean selected;
    protected int type;
    protected boolean isMoving, isChasing, inBarbedWire;
    protected int speed;
    protected float moveTimeLeft, shotTimer, deadBodyTimer;
    protected float hp, totalHp, attackDamage, attackAccuracy, accuracyOffset, reloadTime, rangeOfSight, radius;
    protected Unit enemy;
    protected float walkingTimer, cycleTime;
    private int group;
    protected boolean enemyUnit, inSight;
    protected String name;
    protected Image[] sprites;
    protected BaseGameState gameState;
    
    public Unit(Vector2f p, BaseGameState bgs){
        name = "Base Unit";
        selected = false;
        location = lastLoc = lastLoc2 = p;
        lastTarget = new Vector2f(p);
        targets = new ArrayList();
        isMoving = isChasing = false;
        speed = 3;
        type = UNIT;
        hp = totalHp = 10;
        attackDamage = 1;
        attackAccuracy = 0.5f;
        enemy = null;
        reloadTime = 10;
        shotTimer = 0;
        deadBodyTimer = 100;
        walkingTimer = 0;
        cycleTime = 64;
        group = UNGROUPED;
        enemyUnit = false;
        inSight = false;
        rangeOfSight = 100;
        radius = 16;
        accuracyOffset = 50; // the greater, the less distance affects accuracy
        sprites = new Image[8];
        initMovementSprites();
        gameState = bgs;
    }
    
    protected void initMovementSprites(){
        for(int i = 0; i < 8; i++){
            sprites[i] = getSpriteSheet().getSubImage(i * getSpriteSheet().getWidth() / 8, 0, getSpriteSheet().getWidth() / 8, getSpriteSheet().getWidth() / 8);
        }
    }
    
    public static void LoadSprite() throws SlickException{
        walkingSpriteImage = new Image("res/spritesheetT.png");
        deadUnit = new Image("res/deadunitT.png");
        attackingUnit = new Image("res/attackingunitT.png");
    }
    
    protected Image getAttackingImage(){
        return attackingUnit;
    }
    
    public void update(ArrayList<Unit> units, Camera c){
        inSight = false;
        
        updatePos(units, c);
                
        // dead body clean up time
        if(!isAlive()){
            deadBodyTimer--;
            isMoving = false;
            return;
        }
        
        // enemy giving up target enemy
        if(/*isEnemy() &&*/!isChasing && enemy != null && MyMath.distance(location, enemy.location) >= rangeOfSight){
            enemy = null;
        }
        
        // safety precaution so no friendly fire amongst enemies
        /*if(enemy != null && enemy.isEnemy() == isEnemy()){
            enemy = null;
        }*/
        
        // if enemy attacking, then in sight
        /*if(isEnemy() && enemy != null){
            inSight = true;
        }*/
        
        
        //deselect selected enemies not in sight
        if(isEnemy() && !inSight){
            deselect();
        }
        
        // auto enemy detection
        if(isAlive() && enemy == null){
            for(Unit u : units){
                if(u.getType() != Unit.GAIA &&
                        u.getType() != Unit.WALL){
                    
                    if(u.isEnemy() != isEnemy() && 
                            u.isAlive() && 
                            canShoot(u)){
                        setEnemy(u);
                        break;
                    }
                    else if(u.isEnemy() &&
                            u.isEnemy() == isEnemy() && 
                            u.isAlive() && 
                            u.enemy != null){
                        setEnemy(u.enemy);
                        isChasing = true;
                        break;
                    }
                }
            }
        }
        
        // health update if less than 0
        if(hp < 0){
            hp = 0;
        }
        
        // shot timer update
        if(shotTimer > 0){
            shotTimer --;
        }
        // attack enemy
        if(enemy != null){
            // if enemy is dead
            if(!enemy.isAlive()){
                enemy.deselect();
                enemy = null;
                isChasing = false;
            }
            // shoot the enemy
            else{
                if(readyToShoot(units)){
                    if(enemy.getType() == Unit.WALL){
                        attackBarbedWire(units, c);
                    }
                    else{
                        attackEnemy(units, c);
                        inSight = true;
                    }
                }
            }
        }
        
        
        // if enemy is too far away, then target is lost ie. enemy = null
        if(enemy != null && MyMath.distance(location, enemy.getLocation()) > rangeOfSight + 200){
            enemy = null;
            isChasing = false;
        }
    }
    
    protected void attackEnemy(ArrayList<Unit> units, Camera c){
        attack(enemy);
        if(enemy.getEnemy() == null /*|| (enemy.enemy.getType() == Unit.TANK && this.getType() != Unit.TANK)*/){
            enemy.setEnemy(this);
            enemy.setIsChasing(true);
        }
        shotTimer = reloadTime;

        // shooting vfx 
        Vector2f diff = new Vector2f(location.x - enemy.location.x, location.y - enemy.location.y);
        float pushDis = radius;
        Vector2f push = diff.normalise().scale(pushDis);
        c.addVFX(new ShotVFX(location.x - push.x, location.y - push.y, enemy.location.x, enemy.location.y, radius * 2));
        shotSound();
    }
    
    protected void attackBarbedWire(ArrayList<Unit> units, Camera c){
        // purposeful cutting of wire vs walking through
        if(MyMath.distance(location, enemy.location) <= radius + enemy.radius){
            enemy.hp -= 0.1;
        }
    }
    
    private void updatePos(ArrayList<Unit> units, Camera c){
        
        if(!lastLoc.equals(location)){
            lastLoc2 = new Vector2f(lastLoc);
        }
            
        lastLoc = new Vector2f(location);
        
        // update position
            // good collision handling
        
        // unrelated to collision, variable for in sight
        boolean isInSight = false;
        //
        
        // update target to chase enemy
        if(isChasing){
            float distance = MyMath.distance(location, enemy.location);
            if(enemy.getType() == Unit.WALL && getType() != Unit.TANK){
                if(distance > radius + enemy.radius){
                    
                    Vector2f diff = new Vector2f(enemy.location.x - location.x, enemy.location.y - location.y);
                    float travelDis =  distance - radius + enemy.radius + Unit.COLLISIONBUFFER;
                    float bufferDis = 5;
                    Vector2f push = diff.normalise().scale(travelDis + bufferDis);
                    Vector2f inRangePos = new Vector2f(location.x + push.x, location.y + push.y);
                    
                    targets.clear();
                    targets.add(inRangePos);
                }
            }
            else if(distance > rangeOfSight){
                Vector2f diff = new Vector2f(enemy.location.x - location.x, enemy.location.y - location.y);
                float travelDis =  distance - rangeOfSight + Unit.COLLISIONBUFFER;
                float bufferDis = 5;
                Vector2f push = diff.normalise().scale(travelDis + bufferDis);
                Vector2f inRangePos = new Vector2f(location.x + push.x, location.y + push.y);                
                targets.clear();
                targets.add(inRangePos);
        
                if(bulletTrajectoryGood(units) && canShoot(enemy)){
                    targets.clear();
                }
            }
            else{
                targets.clear();
            }
        }
        
        for(Unit u : units){
            
            // unrelated to collisions, checks and sets if enemy is in sight
            if(!isInSight && u.isAlive() && enemyUnit && !u.isEnemy() && u.getType() != Unit.GAIA){
                if(MyMath.distance(location, u.location) < u.rangeOfSight){
                    isInSight = true;
                    setInSight(true);
                }
                else{
                    setInSight(false);
                }
            }
            //
            
            // actual pushing - credits to Duane Byer
            if(u != this &&                     
                MyMath.distance(location, u.getLocation()) < radius + u.radius + Unit.COLLISIONBUFFER){
                //location.x -= speed*xvel;
                //location.y -= speed*yvel;
                
                // if at wall(barbed wire)
                if(u.getType() == Unit.WALL){
                    if(getType() == Unit.TANK){
                        u.hp = 0;
                    }
                    else if(getType() == Unit.UNIT){
                        inBarbedWire = true;
                        if(!targets.isEmpty()){
                            moveTimeLeft = MyMath.distance(location, targets.get(0)) / speed;
                        }
                        //walking through barbed wire while cutting
                        u.hp -= 0.05;
                    }
                }
                
                // if grenade
                if(u.getSubType() == Unit.NONCOLLIDEABLE){
                }
                // if at an immovable obstacle
                else if(u.getType() == Unit.BUILDING ||
                        u.getType() == Unit.NEST ||
                        u.getType() == Unit.GAIA ||
                        (u.getType() == Unit.TANK && getType() == Unit.UNIT)){
                    if(!targets.isEmpty()){
                        moveTimeLeft = MyMath.distance(location, targets.get(0)) / speed;
                    }
                    Vector2f diff = new Vector2f(location.x - u.location.x, location.y - u.location.y);
                    float sumR =  radius + u.radius + Unit.COLLISIONBUFFER ;
                    float pushDis = sumR - diff.length();
                    Vector2f push = diff.normalise().scale(pushDis);
                    location.x += push.x;
                    location.y += push.y;
                }
                
                // if pushing unit out of the way
                else if((u.getType() == Unit.UNIT && getType() == Unit.UNIT) ||
                        (u.getType() == Unit.TANK && getType() == Unit.TANK)) {
                    Vector2f diff = new Vector2f(location.x - u.location.x, location.y - u.location.y);
                    float sumR =  radius + u.radius + Unit.COLLISIONBUFFER ;
                    float pushDis = (sumR - diff.length()) / 2;
                    Vector2f push = diff.normalise().scale(pushDis);
                    if(u.getType() == Unit.TANK && getType() != Unit.TANK){
                        float totalSpeed = speed + u.speed;
                        location.x += push.x * (speed/totalSpeed);
                        location.y += push.y * (speed/totalSpeed);
                        u.location.x -= push.x * (u.speed/totalSpeed);
                        u.location.y -=  push.y * (u.speed/totalSpeed);
                    }
                    else{
                        location.x += push.x;
                        location.y += push.y;
                        u.location.x -= push.x;
                        u.location.y -=  push.y;
                    }
                }

                /*if(u.getType() != Unit.BUILDING)
                    u.push(speed*xvel,speed*yvel, units);
                if(xvel > yvel){
                    location.y -= speed*yvel;
                }
                else{
                    location.x -= speed*xvel;
                }
                break;*/
            }
        }
        if(location.x <= 10){
            location.x = 10;
        }
        if(location.y <= 10){
            location.y = 10;
        }
        if(location.x >= gameState.WIDTH - 10){
            location.x = gameState.WIDTH - 10;
        }
        if(location.y >= gameState.LENGTH - 10){
            location.y = gameState.LENGTH - 10;
        }
                  
        if(targets.isEmpty() || !isAlive()){   
            
            isMoving = false;
        }
        // when to stop
        else if(MyMath.distance(location, targets.get(0)) < 1){
            targets.remove(0);
            if(!targets.isEmpty()){
                moveTimeLeft = MyMath.distance(location, targets.get(0)) / speed;
            }
            
            isMoving = false;
        }
        else if(isMoving && moveTimeLeft < 0){
            targets.remove(0);
            if(!targets.isEmpty()){
                moveTimeLeft = MyMath.distance(location, targets.get(0)) / speed;
            }
            isMoving = false;
        }
        // updating movement
        else{
        
            lastTarget = targets.get(0);
            moveTimeLeft -= 1;
            isMoving = true;
            
            // direction of movement
            /*boolean down, right;
            down = targets.get(0).y - location.y >= 0;
            right = targets.get(0).x - location.x >= 0;*/
            
            // xvel and yvel calculations
            float trueSpeed = inBarbedWire ? speed / 4 : speed;
            inBarbedWire = false;
            Vector2f travelVector = new Vector2f(targets.get(0).x - location.x, targets.get(0).y - location.y).normalise().scale(trueSpeed);
            /*float xvel = (MyMath.xDistance(location, targets.get(0)) /
                    MyMath.distance(location, targets.get(0))),
                    yvel = (MyMath.yDistance(location, targets.get(0)) /
                    MyMath.distance(location, targets.get(0)));*/
            // add vel to pos
            location.x += travelVector.x;
            location.y += travelVector.y;
            
        }
    }
    
    protected void shotSound(){
        Sounds.SOLDIERSHOT.playAt(location.x, location.y, 0);
    }
    
    protected boolean readyToShoot(ArrayList<Unit> units){
        if(bulletTrajectoryGood(units) && shotTimer <= 0 && canShoot(enemy)){
            return true;
        }
        return false;
    }
    
    protected boolean canShoot(Unit enemy){
        return MyMath.distance(location, enemy.location) < rangeOfSight;
    }
    
    public void makeEnemy(){
        enemyUnit = true;
    }
    
    public boolean isEnemy(){
        return enemyUnit;
    }
    
    public boolean bulletTrajectoryGood(ArrayList<Unit> units){
        Line bulletTrajectory;  
        bulletTrajectory = new Line(location.x, location.y, enemy.getLocation().x, enemy.getLocation().y);
        
        for(Unit u : units){
            if((u.getType() == Unit.BUILDING || u.getType() == Unit.GAIA) &&
                    bulletTrajectory.distance(new Vector2f(u.getLocation().x, u.getLocation().y)) < 20){
                enemy = isChasing ? enemy : null;
                return false;
            }
        }
        return true;
    }
    
    public void setInSight(boolean s){
        inSight = s;
    }
    
    public void setGroup(int g){
        group = g;
    }
    
    public int getGroup(){
        return group;
    }
    
    public boolean isAlive(){
        return hp > 0;
    }
    
    public Vector2f getLocation(){
        return location;
    }
    
    public boolean isSelected(){
        return selected;
    }
    
    public void setTarget(Vector2f p){
        
        targets.clear();
        targets.add(p);
        moveTimeLeft = MyMath.distance(location, targets.get(0)) / speed;
    }
    
    public void addTarget(Vector2f p){
        targets.add(p);
        if(targets.size() == 1){
            moveTimeLeft = MyMath.distance(location, targets.get(0)) / speed;
        }
    }
    
    public void attack(Unit enemy){
        float actualAccuracy = attackAccuracy, distance = MyMath.distance(location, enemy.location);
        
        actualAccuracy -= (float) (distance - accuracyOffset) / (float) distance;
        
        if(Math.random() < actualAccuracy){
            enemy.hp -= attackDamage + Math.random();
        }
    }
    
    public void setEnemy(Unit u){
        enemy = u;
    }
    
    public void select(){
        selected = true;
    }
    
    public void deselect(){
        selected = false;
    }
    
    public int getType(){
        return type;
    }
    
    public int getSubType(){
        return getType();
    }
    
    public float getRadius(){
        return radius;
    }
    
    public Unit getEnemy(){
        return enemy;
    }
    
    public boolean isInSight(){
        return inSight;
    }
    
    public void setHp(float nhp){
        hp = nhp;
    }
    
    public float getHp(){
        return hp;
    }
    
    public float getTotalHp(){
        return totalHp;
    }
    
    public float getRangeOfSight(){
        return rangeOfSight;
    }
    
    public void setIsChasing(boolean chasing){
        isChasing = chasing;
    }
    
    public boolean mustRemove(){
        if(deadBodyTimer == 0){
            if(isEnemy()){
                Player.kills ++;
                Player.morale += 50;
            }
            else{
                Player.losses ++;
            }
        }
        return deadBodyTimer == 0;
    }
    
    public Image getSidebarImage(){
        return sprites[0];
    }
    
    public Image getUnitOnMapImage(){
        return getSidebarImage().getScaledCopy((int)radius, (int)radius);
    }
    
    public void renderSidebar(Graphics g){
        Image sprite;
        
        sprite = getSidebarImage();
        
        g.setColor(Color.yellow);
        g.drawString(name, 0, 0);
        g.drawImage(sprite.getScaledCopy(60, 60), 20,20);
    }
            
    public ArrayList<Button> getButtons(){
        return new ArrayList<Button>();
    }
    
    protected Image getSpriteSheet(){
        return walkingSpriteImage;
    }
    
    protected Image getSpriteImage(){
        //sprite logic update
        if(isMoving){
            walkingTimer ++;
            if(walkingTimer >= cycleTime){
                walkingTimer = 0;
            }
        }
        else{
            walkingTimer = 0;
        }
        Image sprite;
                       
        if(isAlive() && enemy != null && enemy.getType() != Unit.WALL){
            sprite = getAttackingImage().getScaledCopy((int)radius*2, (int)radius*2);
            sprite.rotate(-(float)Math.toDegrees(Math.atan2(-enemy.location.x + location.x, -enemy.location.y + location.y)));
            return sprite;
        }
        
        if(isAlive()){
            sprite = sprites[(int) MyMath.div(walkingTimer, 8)].getScaledCopy((int)radius*2, (int)radius*2);
        }
        else{
            sprite = deadUnit.getScaledCopy((int)radius*2, (int)radius*2);
        }
        //sprite.rotate(-(float)Math.toDegrees(Math.atan2(lastLoc.x - location.x, lastLoc.y - location.y)/*(lastTarget.x - location.x, lastTarget.y - location.y)*/));
        
        float rotDeg = -(float)Math.toDegrees(Math.atan2(lastLoc2.x - location.x, lastLoc2.y - location.y)/*(lastTarget.x - location.x, lastTarget.y - location.y)*/);
        
        /*if(!isMoving){
            rotDeg = -(float)Math.toDegrees(Math.atan2(lastTarget.x - location.x, lastTarget.y - location.y));
        
        }*/
        sprite.rotate(rotDeg);
        
        return sprite;
    }
    
    public void renderSprite(Graphics g, Camera c){
        
        // sprite draw
        /*walkingSprite.renderInUse((int)location.x, (int)location.y,
                9, 9,
                (float) Math.atan2(location.x - lastTarget.x, location.y - lastTarget.y),
                (int) (walkingTimer / 4) + 1, 1);*/
        g.drawImage(getSpriteImage(), location.x - c.pos.x - radius, location.y  - c.pos.y - radius);
    }
    
    protected void renderHealth(Graphics g, Camera c){
        // health bar
            float percentHp = (hp / totalHp);
            if(percentHp < 0.30){
                g.setColor(Color.red);
            }
            else if(percentHp < 0.50){
                g.setColor(Color.orange);
            }
            else if(percentHp < 0.70){
                g.setColor(Color.yellow);
            }
            else{
                g.setColor(Color.green);
            }
            g.fillRect(location.x - c.pos.x - radius, location.y  - c.pos.y + radius + 6,  percentHp * radius * 2, 2);
    }
    
    public void render (Graphics g, Camera c){
        /*if(isAlive()){
            g.setColor(Color.white);
        }
        else{
            g.setColor(Color.red);
        }
        g.draw(new Circle(location.x - c.pos.x, location.y  - c.pos.y, radius));*/
        // if enemy and not attacking nor in sight
        if(isEnemy() && isAlive() && !inSight){
            return;
        }        
        
        renderSprite(g, c);
        
        if(selected && isAlive()){
            
            g.setColor(Color.green);
            // group number
            if(group != UNGROUPED){
                g.drawString("" + (group - 1), location.x - c.pos.x + radius, location.y  - c.pos.y + radius + 2);
            }
            // show selected
                // enemy has red selection circle
            /*if(enemyUnit){
                g.setColor(Color.red);
            }
            g.draw(new Circle(location.x - c.pos.x, location.y  - c.pos.y, 10));*/
            renderHealth(g, c);
        }
        // label enemy
        if(enemyUnit){
            
            g.setColor(Color.red);
            g.drawString("E", location.x - c.pos.x + radius, location.y  - c.pos.y + radius + 2);
        }
    }
    
    public Unit copy(){
        return new Unit(location, gameState);
    }
    
    @Override
    public String toString(){
        int isenemy = isEnemy() ? 1 : 0;
        return getSubType() + " " + isenemy + " " + location.x + " " + location.y;
    }
    
    public static Unit stringToUnit(String s, BaseGameState bgs){
        Scanner parseU = new Scanner(s);
        int type = parseU.nextInt(), isenemy = parseU.nextInt();
        float x = parseU.nextFloat(), y = parseU.nextFloat();
        Unit u;
        if(type == Unit.BUILDING){
            u = new Building(new Vector2f(x, y), bgs);
        }
        else if(type == Unit.ENGINEER){
            u =  new NestEngineer(new Vector2f(x, y), bgs);
        }
        else if(type == Unit.SCOUT){
            u =  new Scout(new Vector2f(x, y), bgs);
        }
        else if(type == Unit.SNIPER){
            u =  new Sniper(new Vector2f(x, y), bgs);
        }
        else if(type == Unit.TANK){
            u =  new Tank(new Vector2f(x, y), bgs);
        }
        else if(type == Unit.GRENADIER){
            u =  new Grenadier(new Vector2f(x, y), bgs);
        }
        else if(type == Unit.GAIA){
            u =  new Gaia(new Vector2f(x, y), bgs);
        }
        else if(type == Unit.WALL){
            u =  new Wall(new Vector2f(x, y), 0, 2, bgs);
        }
        else{
            u =  new FootSoldier(new Vector2f(x, y), bgs);
        }
        
        if(isenemy == 1){
            u.makeEnemy();
        }
        
        return u;
    }
}
