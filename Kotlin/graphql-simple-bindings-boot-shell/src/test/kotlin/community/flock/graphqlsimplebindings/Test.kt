interface A {
    val a:String
}

interface B {
    val b:String
}

interface C {
    val c:String
}

interface AB: A,B
val obj: AB = object:AB{
    override val a: String get() = "A"
    override val b: String get() = "B"
}

fun test(){
    obj.a
    obj.b
}
