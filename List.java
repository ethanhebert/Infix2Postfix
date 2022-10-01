
/* ***************************************************
 * <your name> Ethan Hebert
 * <the date> 10-22-21
 * <the file name> List.java
 *
 * <a simple, short program/class description> Implements a list that can be
 * altered with certain commands using a linked list as the backend data structure.
 * This allows for generics, so it works with integers, characters, floats, etc.
 *************************************************** */

//so basically you added a parameter for the Node constructor called data. previously if you just did integers, the dafault data 
//was -1, if you did characters, the default data was null. now that there are multiple data types, you can't do any one of these.
//so when you made a new list you make a variable called "data" of type "type" that is equal to nothing. then when you call Node with
//this "data", the dafualt for any data type will be nothing as you wish

//putting <type> in the class headers means type is being used in the whole class, so it doesn't have to be written in the function headers


// the Node class
class Node<type>
{
	private type data;
	private Node<type> link;

	// constructor
	public Node(type data)
	{
		this.data = data;
		this.link = null;
	}

	// accessor and mutator for the data component
	public type getData()
	{
		return this.data;
	}

	public void setData(type data)
	{
		this.data = data;
	}

	// accessor and mutator for the link component
	public Node<type> getLink()
	{
		return this.link;
	}

	public void setLink(Node<type> link)
	{
		this.link = link;
	}
}

// the List class
public class List<type>
{
	public static final int MAX_SIZE = 1024;

	private Node<type> head;
	private Node<type> tail;
	private Node<type> curr;
	private int num_items;
	type data;

	// constructor
	// remember that an empty list has a "size" of 0 and its "position" is at -1
	public List()
	{
		head = tail = curr = null;
		num_items = 0;
	}

	// copy constructor
	// clones the list l and sets the last element as the current
	public List(List<type> l)
	{
		head = tail = curr = null;
		num_items = 0;
		
		Node<type> temp = l.curr; //save the previous location of curr in List l
		l.First(); //bring curr of List l to the head of List l
		for (int i=0; i<l.GetSize(); i++) //copy the whole List l
		{
			this.InsertAfter(l.curr.getData());
			l.Next();
		}
		l.curr = temp; //restore curr to its previous position in List l
	}

	// navigates to the beginning of the list
	public void First()
	{
		curr = head;
	}

	// navigates to the end of the list
	// the end of the list is at the last valid item in the list
	public void Last()
	{
		curr = tail;
	}

	// navigates to the specified element (0-index)
	// this should not be possible for an empty list
	// this should not be possible for invalid positions
	public void SetPos(int pos)
	{
		if (!IsEmpty() && pos>=0 && pos<=GetSize()-1)
		{
			First();
			for (int i=0; i<pos; i++)
				Next();
		}
	}

	// navigates to the previous element
	// this should not be possible for an empty list
	// there should be no wrap-around
	public void Prev()
	{
		if (!IsEmpty() && GetPos() != 0) //you cant go to the item before an empty list or a list with 1 item
		{
			int location = GetPos(); //find the location of curr
			location--; //go to one before curr
			Node<type> temp = head;
			for (int i=0; i<location; i++)
			{
				temp = temp.getLink();
			}
			curr = temp;
		}
	}

	// navigates to the next element
	// this should not be possible for an empty list
	// there should be no wrap-around
	public void Next()
	{
		if (!IsEmpty() && curr != tail) //doesn't work for empty list or curr at last element
			curr = curr.getLink();
	}

	// returns the location of the current element (or -1)
	public int GetPos()
	{
		if (IsEmpty())
			return -1;
		
		Node<type> temp = head;
		int location = 0;
		while (temp != curr) //keep going one node at a time checking if temp is equal to curr
		{
			temp = temp.getLink();
			location++;
		}

		return location;
	}

	// returns the value of the current element (or -1)
	public type GetValue()
	{
		if (IsEmpty())
			return data;
		return curr.getData();
	}

	// returns the size of the list
	// size does not imply capacity
	public int GetSize()
	{
		return num_items;
	}

	// inserts an item before the current element
	// the new element becomes the current
	// this should not be possible for a full list
	public void InsertBefore(type data)
	{
		if (!IsFull())
		{
			if (GetPos() == 0) //if curr is at the very start of the list (head)
			{
				Node<type> temp = new Node<type>(data);
				temp.setData(data);
				temp.setLink(curr);
				head = curr = temp;
				num_items += 1;
			}

			else //if curr is anywhere else
			{
				Prev();
				InsertAfter(data);
			}
		}
	}

	// inserts an item after the current element
	// the new element becomes the current
	// this should not be possible for a full list
	public void InsertAfter(type data)
	{
		if (!IsFull())
		{
			//if list is empty, then start it
			if (IsEmpty())
			{
				head = new Node<type>(data);
				head.setData(data);
				tail = curr = head;
			}

			//if list isn't empty
			else
			{
				//works for curr at the end of list
				if (curr == tail)
				{
					curr.setLink(new Node<type>(data));
					curr = curr.getLink();
					curr.setData(data);
					tail = curr;
				}

				//works for curr at middle of the list
				else
				{
				Node<type> temp = new Node<type>(data);
				temp.setData(data);
				temp.setLink(curr.getLink());
				curr.setLink(temp);
				curr = temp;
				}
			}

			num_items += 1;
		}
	}

	// removes the current element (collapsing the list)
	// this should not be possible for an empty list. If possible,
	// following element becomes new current element.
	public void Remove()
	{
		if (!IsEmpty())
		{
			if (GetPos() == 0) //if curr is at the first element
			{
				if (GetSize() == 1) //if there is only this one element, delete it
					head = curr = tail = null;
				else
				{
					head = curr.getLink();
					First();
				}
			}

			else if (curr == tail) //if curr is at the tail
			{
				Prev();
				curr.setLink(null);
				tail = curr;
			}

			else //if curr is in the middle, just skip the node with the links
			{
				Prev();
				curr.setLink(curr.getLink().getLink());
				Next();
			}

			num_items -= 1;
		}
	}

	// replaces the value of the current element with the specified value
	// this should not be possible for an empty list
	public void Replace(type data)
	{
		if (!IsEmpty())
			curr.setData(data);
	}

	// returns if the list is empty
	public boolean IsEmpty()
	{
		if (GetSize() == 0)
			return true;
		return false;
	}

	// returns if the list is full
	public boolean IsFull()
	{
		if (num_items >= MAX_SIZE)
			return true;
		return false;
	}

	// returns if two lists are equal (by value)
	public boolean Equals(List<type> l)
	{
		if (this.GetSize() == l.GetSize()) //make sure they're the same size first
		{
			Node<type> thisTemp = this.head;
			Node<type> lTemp = l.head;
			for (int i=0; i<this.GetSize(); i++) //compare data values at every node
			{
				if (thisTemp.getData() != lTemp.getData())
					return false;
				thisTemp = thisTemp.getLink();
				lTemp = lTemp.getLink();
			}
			return true;
		}
		return false;
	}

	// returns the concatenation of two lists
	// l should not be modified
	// l should be concatenated to the end of *this
	// the returned list should not exceed MAX_SIZE elements
	// the last element of the new list is the current
	public List<type> Add(List<type> l)
	{
		if (this.IsEmpty())
			return l;
		else if (l.IsEmpty())
			return this;
		else
		{
			List<type> sum = new List<type>(this); //make a copy of the first List
			Node<type> temp = l.curr; //temporarily store the previous location of curr in List l
			l.First();

			for (int i=0; i<l.GetSize(); i++) //append elements from List l to List sum
			{
				sum.InsertAfter(l.curr.getData());
				l.Next();
			}

			l.curr = temp; //return curr to its previous value in List l
			return sum;
		}
	}

	// returns a string representation of the entire list (e.g., 1 2 3 4 5)
	// the string "NULL" should be returned for an empty list
	public String toString()
	{
		if (IsEmpty())
			return "NULL";
		else
		{
			Node<type> temp = curr; //store the previous value of curr so we can return it after this loop
			First();
			String result = "";
			for (int i = 0; i < num_items; i++)
			{
				result += curr.getData() + ""; //append the data
				curr = curr.getLink(); //move to the next link
			}
			curr = temp; //restore curr to its original value
			return result;
		}
	}
}
