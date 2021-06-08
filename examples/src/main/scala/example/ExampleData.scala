package example
import caliban.schema.Annotations.GQLInterface

import example.ExampleData.Origin.{ BELT, EARTH, MARS }
import example.ExampleData.Role.{ Captain, Engineer, Mechanic, Pilot }

object ExampleData {


  sealed trait Origin

  object Origin {
    case object EARTH extends Origin
    case object MARS  extends Origin
    case object BELT  extends Origin
  }

  sealed trait Role

  object Role {
    case class Captain(shipName: String)  extends Role
    case class Pilot(shipName: String)    extends Role
    case class Engineer(shipName: String) extends Role
    case class Mechanic(shipName: String) extends Role
  }

  case class Character(name: String, nicknames: List[String], origin: Origin, role: Option[Role])

  case class CharactersArgs(origin: Option[Origin])
  case class CharacterArgs(name: String)

  val sampleCharacters = List(
    Character("James Holden", List("Jim", "Hoss"), EARTH, Some(Captain("Rocinante"))),
    Character("Naomi Nagata", Nil, BELT, Some(Engineer("Rocinante"))),
    Character("Amos Burton", Nil, EARTH, Some(Mechanic("Rocinante"))),
    Character("Alex Kamal", Nil, MARS, Some(Pilot("Rocinante"))),
    Character("Chrisjen Avasarala", Nil, EARTH, None),
    Character("Josephus Miller", List("Joe"), BELT, None),
    Character("Roberta Draper", List("Bobbie", "Gunny"), MARS, None)
  )
  
  sealed trait NodeType

  object NodeType {
    case object  MSSQLDATABASE extends NodeType
    case object  MSSQLINSTANCE extends NodeType 
    case object  OS            extends NodeType 
    case object  VM            extends NodeType 
    case object  HOST          extends NodeType 
    case object  DISK          extends NodeType 
    case object  DATASTORE     extends NodeType 
    case object  VOLUME        extends NodeType
  }

  @GQLInterface
  sealed trait Node {
    def uuid: String
    def name: String
  }

  object Node {
      def getNodes: List[Node] = List.empty
      def getNode(uuid: String): Option[Node] = None
  }

  case class MsSqlDatabase(uuid: String, name: String, databaseUid: String) extends Node
  case class MsSqlDatabaseInstance(uuid: String, name: String, instanceUid: String) extends Node
  case class OperatingSystem(uuid: String, name: String, osUid: String, osType: String) extends Node
  case class VirtualMachine(uuid: String, name: String, vmUid: String) extends Node
  case class Host(uuid: String, name: String, hostUid: String) extends Node
  case class Disk(uuid: String, name: String, diskUid: String) extends Node
  case class Datastore(uuid: String, name: String, datastoreUid: String) extends Node
  case class Volume(uuid: String, name: String, volumeUid: String) extends Node


  case class MsSqlDatabaseArgs(uuid: Option[String])
  
  val sampleMsSqlDatabases = List(
    MsSqlDatabase("uuid0", "db0", "uid0"),
    MsSqlDatabase("uuid1", "db1", "uid1"),
    Volume("uuid2", "db2", "uid2")
  )
  
  @GQLInterface
  sealed trait Connection {
    def from: Node
    def to: Node
  }

  case class ParentOf(from: Node, to: Node) extends Connection
  case class RunningOn(from: Node, to: Node) extends Connection
  case class DependsOn(from: Node, to: Node) extends Connection
  case class HostedBy(from: Node, to: Node) extends Connection
  case class FailoverOption(from: Node, to: Node, active: Boolean, standby: Boolean) extends Connection

  case class Topology(vertex: List[Node], edges: List[Connection])
  
  case class TopologyArgs(nodeType: NodeType)

  val sampleEdges = List[Connection](ParentOf((MsSqlDatabase("uuid0", "db0", "uid0")), (Volume("uuid2", "db2", "uid2"))));
  //val sampleTopology = Topology(sampleMsSqlDatabases, emptyList);
}
