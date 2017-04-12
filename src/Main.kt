/**
 * Created by Sergey Skaredov.
 * SPSU. 2017.
 */

fun main(args: Array<String>) {

    var tree: TreeInterface<Int, Int>
    var key: Int?
    var value: Int?

    while (true) {
        println("""Choose kind of tree:
    1 - binary search tree
    2 - red-black tree
    3 - b-tree
    else - end of program""")

        val c = readLine()

        when (c) {
            "1" -> {
                tree = BinarySearchTree<Int, Int>()
            }
            "2" -> {
                tree = RedBlackTree<Int, Int>()
            }
            "3" -> {
                println("Enter the minimum number of keys")
                val t = readLine()!!.toInt()
                tree = BTree<Int, Int>(t)
            }
            else -> return
        }


        loop@ while (true) {

            println("""Choose action:
    1 - enter <key value> //for stop enter ' . '
    2 - find value by key
    3 - delete value by key
    4 - print tree
    else - end work with tree""")

            val choice = readLine()

            when (choice) {
                "1" -> {
                    var string = readLine()

                    while (string != ".") {

                        if (string != null) {

                            try {

                                key = string.takeWhile { it in "-0123456789" }.toInt()
                                value = string.takeLastWhile { it in "-0123456789" }.toInt()

                            } catch (error: Throwable) {

                                println("Error: Illegal argument! Try again")
                                break

                            }

                            tree.insert(key, value)
                        }
                        string = readLine()

                    }
                }
                "2" -> {

                    key = readLine()?.toInt()
                    println(tree.search(key).toString())

                }
                "3" -> {

                    key = readLine()?.toInt()
                    tree.delete(key)

                }
                "4" -> {

                    tree.print()
                }

                else -> {
                    break@loop
                }

            }
        }
    }

}