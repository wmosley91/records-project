/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package music.business;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.util.*;
import music.data.ProductDB;

public class willDeplete extends TagSupport
{
    private ArrayList<LineItem> lineitems;
    private LineItem item;
    
    @Override
    public int doStartTag() throws JspException
    {
       LineItem item = (LineItem) pageContext.findAttribute("item");
       int quantity = item.getQuantity();
       Product p = item.getProduct();
       int avail = ProductDB.getAvailable(p.getId());
       if (avail - quantity == 0)
       {
           return EVAL_BODY_INCLUDE;
       }
       else return SKIP_BODY;
    }
}
