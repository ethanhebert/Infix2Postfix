/*
Ethan Hebert
11-1-21
Queue.java
A class that implements the queue data structure using a linked list.
*/

class Queue<type>
{
    private List<type> list;

    //constructor
    public Queue()
    {
        list = new List<type>();
    }

    //clone constructor
    public Queue(Queue s)
    {
        list = new List<type>(s.list);
    }

    public void Enqueue(type data)
    {
        list.Last();
        list.InsertAfter(data);
    }

    public type Dequeue()
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

    //returns if 2 queues are identical
    public boolean Equals(Queue<type> other)
    {
        return this.list.Equals(other.list);
    }

    //concatenates other queue to the end of this queue
    public Queue<type> Add(Queue<type> other)
    {
        Queue<type> newQueue = new Queue<type>(this);
        newQueue.list = newQueue.list.Add(other.list);
        return newQueue;
    }

    public String toString()
    {
        return list.toString();
    }
}
