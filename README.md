Railway
=======

#需求

建造一个火车站系统，具有以下功能：

- (1) 可以加入某一站点或一对相邻站点，加入相邻站点时，可以设置站点间距离
- (2) 可以查询某两个站点之间是否可达(直接连接或通过其他站点中转)
- (3) 可以查询两个站点之间最少的中转次数，不可达则为0xffff，自己到自己为1次中转，到相邻的下一节点为2次
- (4) 可以查询两个站点之间的最短路径，不可达则为0xffff，自己到自己路径距离为0，其他按照站点间距离累加

#提示

- 1.需要实现以下功能接口：
```java
public interface RailwaySystem {
  /** 增加从srcStation到dstStation的路由；dstStation为空字符串时，仅将srcStation加入系统，并且distance填0 */
  void addRoute(String srcStation, String dstStation, int distance);
  
  /** 查询srcStation是否可以到达dstStation */
  boolean canReach(String srcStation, String dstStation);
  
  /** 查询srcStation到dstStation的最小中转数 */
  int getMinHopCount(String srcStation, String dstStation);
  
  /** 查询srcStation到dstStation的最短路径 */
  int getMinCostPath(String srcStation, String dstStation);
}
```

#场景

