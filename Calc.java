// Postfix Calculator Applet
//
// CS 201 HW 8
// jforsberg 

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*; // for Stack

public class Calc extends Applet implements ActionListener {

    // instance variables
	Label titleLabel;
    Label result;         // label used to show result
    Stack<Integer> stack; // stack used for calculations
    int current;          // current number being entered
    boolean entered;      // whether current number has been entered
                          // if so, show number in red

    // local colour constants
    static final Color black = Color.black;
    static final Color white = Color.white;
    static final Color red = Color.red;
    static final Color green = Color.green;
    static final Color blue = Color.blue;
    static final Color yellow = Color.yellow;
    static final Color dred = new Color(160, 0, 100);
    static final Color dgreen = new Color(0, 120, 90);
    static final Color dblue = Color.blue.darker();

    //sets everything up
    public void init() {

        // Font to use in applet
        setFont(new Font("TimesRoman", Font.BOLD, 14));
        result = new Label("0");
        stack = new Stack<Integer>();
        current = 0;
        entered = false;

        result.setForeground(dgreen);//make result green

        setLayout(new BorderLayout());
        add("North", resultPanelBorder());  // add result at top
        add("South", bottomButtons()); // add buttons at bottom
        add("Center", buttons());  // add buttons in centre 
    }
    
    public Panel buttons() {
        
        Panel b = new Panel();
        b.setLayout(new GridLayout(4,4));
        
        b.add(CButton("7", dgreen, yellow));
        b.add(CButton("8", dgreen, yellow));
        b.add(CButton("9", dgreen, yellow));
        b.add(CButton("+", dred, dred));
        b.add(CButton("4", dgreen, yellow));
        b.add(CButton("5", dgreen, yellow));
        b.add(CButton("6", dgreen, yellow));
        b.add(CButton("-", dred, dred));
        b.add(CButton("1", dgreen, yellow));
        b.add(CButton("2", dgreen, yellow));
        b.add(CButton("3", dgreen, yellow));
        b.add(CButton("*", dred, dred));
        b.add(CButton("0", dgreen, yellow));
        b.add(CButton("(-)", dgreen, yellow));
        b.add(CButton("Pop", dgreen, yellow));
        b.add(CButton("/", dred, dred));
  
        this.add(b);
        return b;   
    } 
    //creates 'enter' and 'clear' buttons
    public Panel bottomButtons() {
    	
        Panel b = new Panel();
        b.setLayout(new GridLayout(1,2));
        b.add(CButton("Enter", dgreen, dgreen));
        b.add(CButton("Clear", dgreen, dgreen));
       
        this.add(b);
        return b;
    } 
    
    //helps create the border around result
    public Panel resultPanelBorder(){
    	
    	Panel r = new Panel();
    	r.setLayout(new BorderLayout());
    	r.add("Center", resultLine());
    	setBackground(blue);
    	r.add("South", new Label());
    	r.add("North", new Label());
    	r.add("East", new Label());
    	r.add("West", new Label());

    	this.add(r);
    	return r;
    }
    
    //creates the result line with white background
    public Panel resultLine(){
    	
    	Panel res = new Panel();
    	res.setLayout(new BorderLayout());
    	res.setBackground(white);
    	res.add("East", result);
    	
    	this.add(res);
    	return res;
    }
 
    // create a coloured button
    protected Button CButton(String s, Color fg, Color bg) {
        Button b = new Button(s);
        b.setBackground(bg);
        b.setForeground(fg);
        b.addActionListener(this);
        return b;
    }

    // handles button clicks
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof Button) {
            String label = ((Button)e.getSource()).getLabel();
            
            colourChange();//changes colour of result
            
            if (label.equals("+"))
                add();
            else if (label.equals("-"))
                sub();
            else if (label.equals("*"))
                mult();
            else if (label.equals("/"))
                div();
            else if (label.equals("(-)")){
            	if (entered == false){
            	current = -current;
            	show(current);
            		enter();	
            	}
            }
            else if (label.equals("Pop")){
            	if (stack.size() >1){
            		stack.pop();
            		current = stack.peek();
            	}
            	else{
            		stack.pop();
            		current = 0;
            	}
            	show(current);
            }
            else if (label.equals("Enter")){
            	enter(); 
            }
            else if (label.equals("Clear")){
            	while (stack.empty() == false)
            		stack.pop();
            		current = 0;
            		show(current);
            }	
            else {     // number button
                int n = Integer.parseInt(label);
                number(n); 
            }
        }
    }

    //enters numbers
    public void enter(){
    	 //add the current to the stack
        entered = true;
        stack.push(current);
        current = 0;    
    }
    
    // display number n in result label
    protected void show(int n) {
        result.setText(Integer.toString(n));
    }

    // handle add button
    protected void add() {
        //pop 2 numbers add second to first
    	if (entered == false)
    		enter();
    	
    	if(stack.size() > 1){
    		int a = stack.pop();
    		int b = stack.pop();
    		current = b + a;
    		show(current);
    		enter();
    	}		
    }
    
    //handle subtraction
    protected void sub() {
    	//pop the previous 2 numbers 
    	//subtract the second from the first
    	
    	if (entered == false)
    		enter();
    	
    	if(stack.size() > 1){
    		int a = stack.pop();
    		int b = stack.pop();
    		current = b - a;
    		show(current);
    		enter();
    	}
    }
    
    //handle multiplication
    protected void mult() {
    	if (entered == false)
    		enter();
    	if(stack.size() > 1){
    		int a = stack.pop();
    		int b = stack.pop();
    		current = b * a;
    		enter();
    	}
    	else{
    		stack.pop();
    		stack.push(0);
    	}
       show(stack.peek());
    }
    
    protected void div() {
    	if (entered == false)
    		enter();
    	if(stack.size() > 1){
    		int a = stack.pop();
    		int b = stack.pop();
    		current = b/a;
    		enter();
    	}
    	else{
    		stack.pop();
    		stack.push(0);
    	}
        show(stack.peek());
    }
    
    //changes colour of result 
    //if it has been entered
    public void colourChange(){
    	if (entered == true)
        	result.setForeground(dgreen);
        else
        	result.setForeground(red);
    }

    // handle number buttons
    protected void number(int n) {
    	current = current*10 + n;
        show(current);
        entered = false;
        
    }
}
