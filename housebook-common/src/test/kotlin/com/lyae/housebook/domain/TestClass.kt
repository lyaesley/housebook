package com.lyae.housebook.domain

/**
 *  ?. (안전한 호출 연산자) 어떤 방식으로 실행되는지 보기 위해 만든 클래스
 *  1. mutableListOf() 일 경우 어떻게 작동하지?
 */

class TestClass(
    val someList: MutableList<ParentClass> = mutableListOf(),
    val name: String = "테스트",
)

class ParentClass(
    var testClassObj: TestClass? = null
){

    fun addTestClassObj(paramTestClassObj: TestClass) {
        println("출력 1 : ${this.testClassObj}")
        println("출력 2 : ${this.testClassObj?.someList}")
        println("출력 3 : ${this.testClassObj?.someList?.remove(this)}")

        paramTestClassObj.name

    }
}

fun main() {
    val testClass = TestClass()
    val parentClass = ParentClass()

    println("=======호출 1")
    parentClass.addTestClassObj(testClass)

    println("=======호출 2")
    parentClass.testClassObj = testClass
    parentClass.addTestClassObj(testClass)
    /** 결과
     * 출력 1 : com.lyae.housebook.domain.TestCalss@4e515669
     * 출력 2 : []
     * 출력 3 : false
     */

    println("=======호출 3")
    testClass.someList.add(parentClass)
    parentClass.addTestClassObj(testClass)
    /**
     * 출력 1 : com.lyae.housebook.domain.TestCalss@4e515669
     * 출력 2 : [com.lyae.housebook.domain.ParentClass@17d10166]
     * 출력 3 : true
     */


}

