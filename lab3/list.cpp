// Vi behöver klassdeklarationerna...
#include "list.h"
// ...och standardfunktioner för I/O med mera
#include <iostream>
#include <algorithm>

// Vi behöver använda saker från namnrymden std
using namespace std;

List::List() :
    first_node(new End_Node()), length(0)
{}

void List::insert_sorted(const int value)
{
    first_node = first_node->insert_sorted(value);
    length++;
}

List_Node::List_Node() {}

End_Node::End_Node() {}

Int_Node::Int_Node(const int value, List_Node* next) : 
  node_value(value), next_node(next)
{}

Int_Node* Int_Node::insert_sorted(const int value)
{
    // Om värdet är mindre än denna nods värde
    // ska det stoppas in före denna nod i listan
    if (value < node_value) {
        // Här allokerar vi minne för en ny nod som ska placeras
        // före denna.  Om minnesallokeringen misslyckas vill vi
        // inte förstöra datastrukturen, vilket vi inte gör
        // eftersom vi inte har gjort några ändringar innan
        // allokeringen.
        return new Int_Node(value, this);
    } else {
        // Värdet ska stoppas in efter denna nod.  Det kan hända
        // att den stoppas in *direkt* efter denna nod.  I så
        // fall returnerar det rekursiva anropet till
        // insert_sorted en ny nod som vi får som vår nya "nästa"
        // nod.
        next_node = next_node->insert_sorted(value);
        return this;
    }
}

Int_Node* End_Node::insert_sorted(const int value)
{
    // Vi har nått slutet på listan.  Elementet vi skulle
    // lägga till var större än alla existerande element i
    // listan.  Den nya noden ska vara omedelbart före denna
    // End_Node.

    // Om allokering misslyckas backar vi bara ur rekursionen,
    // som i Int_Node ovan.
    return new Int_Node(value, this);
}

// Vi måste deklarera en virtuell destruktor i List_Node även om
// den inte gör något -- annars kommer inte destruering att
// fungera korrekt när vi gör delete på en List_Node-pekare!
// Vi kan inte förlita oss på standarddestruktorn som används om
// vi inte själva deklarerar en destruktor, eftersom denna INTE
// är virtuell.
List_Node::~List_Node() {}

// När vi destruerar en Int_Node ska vi även rekursivt destruera
// den nod den pekar på (vilket då fortsätter ända till kedjan
// "tar slut").  Här behöver vi inte säga att destruktorn ska
// vara virtual -- det blir den automatiskt eftersom
// superklassens destruktor är virtual.
Int_Node::~Int_Node() { delete next_node; }

// Notera att vi inte själva skapar en destruktor för End_Node!
// Kompilatorn kommer därför att skapa en defaultimplementation
// för oss, vilket fungerar i detta fall eftersom just End_Node
// inte allokerar minne eller andra resurser.

// Slutligen behöver vi kunna destruera en hel lista.  Vi
// destruerar då dess första nod, vilket rekursivt går genom hela
// listan och destruerar alla noder fram till och med den sista
// End_Node-noden.
List::~List()
{
    delete first_node;
}

Int_Node* Int_Node::clone() const
{
    // Försök först att kopiera resten av listan.  Om detta
    // misslyckas returnerar funktionen (ett undantag kastas),
    // men det är OK för vi har inte allokerat några resurser
    // eller "förstört" denna datastruktur på något sätt.
    List_Node* p = next_node->clone();

    try {
        // Försök sedan att allokera minne för en ny Int_Node som
        // har samma värde som denna, men pekar på den nya
        // kopierade "svansen".  Om *detta* misslyckas kan vi
        // inte bara returnera som vi gjorde ovan -- vi måste
        // städa upp det minne vi faktiskt lyckades allokera (p)!
        return new Int_Node(node_value, p);
    } catch (...) {
        // Konstruktionen ovan är en "catch-all" som fångar ALLA
        // fel som kan uppstå.
        // Städa upp:  Frigör det minne vi lyckades allokera
        delete p;
        // Kasta vidare det fel som uppstod
        throw;
    }
}

End_Node* End_Node::clone() const
{
  return new End_Node(); 
}

List::List(const List& other) :
  first_node(other.first_node->clone()), length(other.length)
{}

void List::swap(List& other)
{
  List_Node* temp;
  temp = first_node; 
  first_node = other.first_node;
  other.first_node = temp;

  int temp2;   
  temp2 = length;
  length = other.length;
  other.length = temp2;   
}

List& List::operator=(List& rhs)
{
  List list = List(rhs);
  swap(list);
  return *this; 
}

int List::get(const int pos) const
{
  return first_node->get(pos);
}

int Int_Node::get(const int pos) const
{
  if(pos == 0)
  {
    return node_value;
  }  
  else
    {
      return next_node->get(pos-1);
    }
}

int End_Node::get(const int pos) const
{
  return -1; 
}

int List::len() const
{
  return length; 
}

void print(const List& list){

  for(int i = 0; i < list.len(); i++){
    
    cout << list.get(i) << endl; 
    
  }

}

void printcopy(const List list){
  print(list);
}

int main (){

  List list1 = List();
  List list2 = List();

  list1.insert_sorted(3);
  list1.insert_sorted(5);
  list1.insert_sorted(4);
  list1.insert_sorted(2);
  list1.insert_sorted(1);
  cout << "list1 with print" << endl; 
  print(list1);
  
  list2.insert_sorted(8);
  list2.insert_sorted(10);
  list2.insert_sorted(9);
  list2.insert_sorted(7);
  list2.insert_sorted(6);
  cout << "list2 with print" << endl; 
  print(list2);

  cout << "list1 with printcopy" << endl;
  printcopy(list1);
  cout << "list2 with printcopy" << endl;
  printcopy(list2);

  list2 = list1; 
  cout << "list2 copied from list1" << endl;
  print(list2);


  return 0; 
}
