SQL索引
    
	|-- 索引的类型
        |-- B-Tree 索引
            |--概述
                |--B树索引
                    以顺序的方式形成一个树
                    所有的值都是按顺序存储的，并且每一个叶子页到根的距离相同
            |--特点
                |--B-Tree索引能够加快访问数据的速度
                    因为存储引擎不再需要进行全表扫描来获取需要的数据，取而代之的事从索引的根节点开始进行搜索
                    根节点的槽中存放了指向子节点的指针，存储引擎根据这些指针向下层查找
                    通过比较节点页的值和要查找的值可以找到合适的指针进入下层子节点，这些指针实际上定义了子节点页中值的上限和下限
                    最终存储引擎要么是找到对应的值，要么该记录不存在
                |--叶子节点比较特殊，他们的指针指向的事被索引的数据，而不是其他的节点页
                    树的深度和表的大小直接相关
                |--B-Tree对索引列是顺序组织存储的，所以很适合查找范围数据
                    例如一个基于文本域的索引树上，按字母顺序传递连续的值进行查找是非常合适的，所以像"找出所有以I到K开头的名字"这样的查找效率会非常高
                |--ORDER BY 操作
                    因为索引树中的节点是有序的，所以除了按值查找之外，索引还可以用于查询中的ORDER BY操作(按顺序查找)
            |--优点和缺点
                假设查询的是一个美国姓名表(姓在前，名在后)，后面有出生日期
                |--适用范围
                    |--全值匹配
                        全值匹配值的事和索引中的所有列进行匹配
                        例如：查找姓名为 Cuba Allen、出生于1960-01-01 的人(有具体数据)
                    |--匹配最左前缀
                        例如：查找所有姓为 Allen 的人，即只适用索引的第一列(查询顺序在最左的数据)
                    |--匹配列前缀
                        只匹配某一列的值的开头部分
                        例如：查找所有以J开头的姓的人，这里也只适用了索引的第一列(查询顺序在最左数据的第一个字母)
                    |--匹配范围值
                        例如：查找姓在 Allen 和 Barrymore 之间的人，这里也只使用了索引的第一列
                    |--精确匹配某一列并范围匹配另外一列
                        例如朝招所有姓为 Allen，并且名字是字母K开头的人
                    |--只访问索引的查询
                        即查询只需要的索引，而无需访问数据行
                |--一些限制
                    |--如果不是按照索引的最左列开始查找，则无法使用索引
                        例如：无法查找名字为 Bill 的人，因为这列不是最左的数据列
                        类似的，也无法查找姓氏以某个字母结尾的人
                    |--不能跳过索引中的列
                        例如：无法查找姓为 Smith 并且在某个特定日期出生的人
                        如果不指定名，则MySQL只能使用索引的第一列
                    |--如果查询中有某个列的范围查询，则其右边所有列的无法使用索引优化查找
                        例如：查询 "WHERE last_name='Smith' AND first_name LIKE 'J%' AND dob = '1976-12-23'" 
                        这个查询只能使用索引的前两列，因为这里 LIKE 是一个范围条件
        |-- 哈希索引
            |--概述
                |--哈希索引基于哈希表实现，只有精准匹配索引所有列的查询才有效
                    对于每一行数据，存储引擎都会对所有的索引列计算一个哈希码，哈希码是一个较小的值，并且不同键值的行计算出来的哈希码也不一样
                    哈希索引将所有的哈希码存储在索引中，同时在哈希表中保存指向每个数据行的指针
                |--特点
                    |--如果多个列的哈希值相同，索引会以链表的方式存放多个记录指针到同一个哈希条目中
                    |--哈希索引数据是有序的情况下，数据行不一定是有序的
                    |--查找顺序
                        1.计算出需要查询数据对应的哈希值
                        2.查找数据对应的哈希值所在的位置
                        3.找到对应的位置
                        4.比较位置中的值是否和需要查询数据相同
                    |--查询速度快
                        因为索引自身只需要存储对饮的哈希值，所以索引的结构十分紧凑，这也让哈希索引查找的速度非常快
                    |--适用特地场合
                        因为下述的一些限制，哈希索引值适用于某些特定的场合
                        但一旦适用哈希索引，则它带来的性能提升将非常显著
                |--优点和缺点   
                    |--限制
                        |--不能使用索引中的值来避免读取行
                            哈希索引值包含哈希值和行指针，而不存储字段值，所以不能使用索引中的值来避免读取行
                            不过，访问内存中的行的速度很快，所以大部分情况下这一点对性能的影响并不明显
                        |--无法用于排序
                            哈希索引数据并不是按照索引值顺序存储的，所以也就无法用于排序
                        |--不支持部分索引列匹配查找
                            哈希索引页不支持部分索引列匹配查找，因为哈希索引始终是使用索引列的全部内容来计算哈希值的
                            例如：在数据列(A,B)上简历哈希索引，如果查询只有数据列A，则无法使用该索引
                        |--只支持等值比较查询,同时还不支持任何范围查询
                            只支持等值比较查询，例如：=,IN(),<=>
                            不支持任何范围查询，例如：WHERE proce > 100
                        |--有很多哈希冲突时，速度变慢
                            哈希索引访问数据的速度非常快，除非有很多哈希冲突(不同的索引列值却又相同的哈希值)
                            当出现哈希冲突时，存储引擎必须遍历链表中所有的行指针，逐行进行比较，直到找到所有符合条件的行
                        |--哈希冲突变高时，维护操作的代价也会很高
                            如果哈希冲突很多的话，一些索引维护操作的代价也会很高
                            例如：在某个选择性很低(哈希冲突很多)的列上简历哈希索引，那么当从表中删除一行是，存储引擎需要遍历对应哈希值的链表中的每一行，找到并删除对应行的引用，冲突越多，代价越大
        |-- 对于不能使用哈希索引的数据库
            可以自定义一个哈希索引
	
	|-- 索引的优点
		|-- 索引可以让服务器快速地定位到表的指定位置，但出了这个之外，索引还有其他附加作用
			|-- B-Tree索引
				
                    


                