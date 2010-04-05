/*
 * CampusWorldGUI
 *
 * $Id: CampusWorldGUI.java,v 1.1 2010-04-05 17:27:19 mcoletti Exp $
 */

package sim.app.geo.campusworld;

import com.vividsolutions.jts.io.ParseException;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import sim.display.Console;
import sim.display.Controller;
import sim.display.Display2D;
import sim.display.GUIState;
import sim.engine.SimState;
import sim.portrayal.geo.GeomFieldPortrayal;
import sim.portrayal.geo.GeomPortrayal;


/** MASON GUI wrapper for GeoOgrTest3
 *
 * @author mcoletti
 */
public class CampusWorldGUI extends GUIState {

    private Display2D display;
    private JFrame displayFrame;

    private GeomFieldPortrayal walkwaysPortrayal = new GeomFieldPortrayal(true);
    private GeomFieldPortrayal buildingPortrayal = new GeomFieldPortrayal(true);
    private GeomFieldPortrayal roadsPortrayal = new GeomFieldPortrayal(true);
    private GeomFieldPortrayal agentPortrayal = new GeomFieldPortrayal();
    
    public CampusWorldGUI(SimState state)
    {
        super(state);
    }

    public CampusWorldGUI() throws ParseException
    {
        super(new CampusWorld(System.currentTimeMillis()));
    }

    public void init(Controller controller)
    {
        super.init(controller);

        display = new Display2D(300, 300, this, 1);

        display.attach(walkwaysPortrayal, "Walkways");
        display.attach(buildingPortrayal, "Buildings");
        display.attach(roadsPortrayal, "Roads");
        display.attach(agentPortrayal, "Agents");

        displayFrame = display.createFrame();
        controller.registerFrame(displayFrame);
        displayFrame.setVisible(true);
    }

    
    public void start()
    {
        super.start();
        setupPortrayals();
    }

    private void setupPortrayals()
    {
        CampusWorld world = (CampusWorld)state;

        walkwaysPortrayal.setField(world.walkways);
        walkwaysPortrayal.setPortrayalForAll(new GeomPortrayal(Color.CYAN,true));
        //walkwaysPortrayal.setImmutableField(true); 

        buildingPortrayal.setField(world.buildings);
        buildingPortrayal.setPortrayalForAll(new GeomPortrayal(Color.DARK_GRAY,true));
        //buildingPortrayal.setImmutableField(true); 

        roadsPortrayal.setField(world.roads);
        roadsPortrayal.setPortrayalForAll(new GeomPortrayal(Color.GRAY,true));
       // roadsPortrayal.setImmutableField(true); 

        agentPortrayal.setField(world.agents);
        agentPortrayal.setPortrayalForAll(new GeomPortrayal(Color.RED,10.0,true));

        display.reset();
        display.setBackdrop(Color.WHITE);

        display.repaint();
    }

    public static void main(String[] args)
    {
        CampusWorldGUI worldGUI = null;
        
		
		if (args.length > 0 && args[0].equals("-testing"))
			CampusWorld.testing = true;
		
        try
        {
            worldGUI = new CampusWorldGUI();
        }
        catch (ParseException ex)
        {
            Logger.getLogger(CampusWorldGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        Console console = new Console(worldGUI);
        console.setVisible(true);
    }
    
}
