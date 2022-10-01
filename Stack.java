/*
Ethan Hebert
11-1-21
Stack.java
A class that implements the stack data structure using a linked list.
*/

class Stack<type>
{
    private List<type> list;

    //constructor
    public Stack()
    {
        list = new List<type>();
    }

    //clone constructor
    public Stack(Stack<type> s)
    {
        list = new List<type>(s.list);
    }

    public void Push(type data)
    {
        list.First();
        list.InsertBefore(data);
    }

    public type Pop()
    {
        list.First();
        type data = list.GetValue();
        list.Remove();
        return data;
    }

    public type Peek()
    {
        list.First();
        return list.GetValue();
    }

    public int Size()
    {
        return list.GetSize();
    }

    public boolean IsEmpty()
    {
        return list.IsEmpty();
    }

    public boolean IsFull()
    {
        return list.IsFull();
    }

    //see if 2 stacks are identical
    public boolean Equals(Stack other)
    {
        return this.list.Equals(other.list);
    }

    //concatenate 2 stacks together (put other at the end of this)
    public Stack<type> Add(Stack<type> other)
    {
        Stack<type> newStack = new Stack(this);
        newStack.list = newStack.list.Add(other.list);
        return newStack;
    }

    public String toString()
    {
        return list.toString();
    }
}
