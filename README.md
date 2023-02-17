# Bomberman
<a name="ve-dau-trang"/>

## Thành viên
* Hoàng Bảo Khanh - 21020342
* Ngô Đăng Huy - 21020330
* Lớp môn học: INT2215 1, nhóm 2

##  Mục lục
* [1. Hướng dẫn cài đặt và chạy game](#cai-dat)
* [2. Mô tả chung về trò chơi](#mo-ta)
* [3. Các chức năng của trò chơi](#chuc-nang)
* [4. Các kĩ thuật lập trình được sử dụng](#ki-thuat)
* [5. Hỗ trợ](#ho-tro)
* [6. Kết luận](#ket-luan)
* [7. Tham Khảo](#tham-khao)

<a name="cai-dat"/>

## I, Hướng dẫn cài đặt và chạy game
### B1: Tải project về
- Cách 1: Trên Github, chọn Code => Download ZIP  
- Cách 2: Mở terminal tại thư mục muốn lưu project, sử dụng lệnh `git init` để khởi tạo Git, sau đó dùng lệnh `git clone https://github.com/kiu-chan/Bomberman.git` để clone project về máy
### B2: Cài đặt game
- Tải và cài đặt Intellij từ đường link sau: [link](https://www.jetbrains.com/idea/download/#section=windows)
- Mở project và edit Configurations->add new Configuration -> Application
- Modify options chọn add VM options
- VM options thêm --module-path "" --add-modules javafx.controls,javafx.fxml,javafx.media
- Các mục còn lại thêm tương ứng
- Tại đường dẫn đến path dẫn link đến javafx sdk. 
- Ví dụ: --module-path "path\javafx-sdk-19\lib" --add-modules javafx.controls,javafx.fxml,javafx.media
### B3: Chạy game
Nhấp vô nút run để chạy game

 <a name="mo-ta"/>

## II, Mô tả chung về trò chơi
**Thể loại:** `Chiến thuật`

### I, Giới thiệu chung về trò chơi.
Bomberman là một trò chơi kinh điểm của NES.
Người chơi có nhiệm vụ di chuyển bomber đi khắp bản đồ để tiêu diệt quái vật


<a name = "chuc-nang"/>

### II, Các đối tượng của trò chơi

#### 1, Đối tượng tĩnh
- ![](bomberman-starter-starter-2/res/IMG/map/images/map_01.png) Grass là đối tượng mà Bomber và Enemy có thể di chuyển xuyên qua, và cho phép đặt Bomb lên vị trí của nó
- ![](bomberman-starter-starter-2/bomberman-starter-starter-2/res/IMG/map/images/map_02.png) Wall là đối tượng cố định, không thể phá hủy bằng Bomb cũng như không thể đặt Bomb lên được, Bomber và Enemy không thể di chuyển vào đối tượng này
- ![](bomberman-starter-starter-2/res/IMG/map/images/map_03.png) Brick là đối tượng được đặt lên các ô Grass, không cho phép đặt Bomb lên nhưng có thể bị phá hủy bởi Bomb được đặt gần đó. Bomber và Enemy thông thường không thể di chuyển vào vị trí Brick khi nó chưa bị phá hủy.
- ![](bomberman-starter-starter-2/res/IMG/map/images/map_07.png) Portal là đối tượng được giấu phía sau một đối tượng Brick. Khi Brick đó bị phá hủy, Portal sẽ hiện ra và nếu tất cả Enemy đã bị tiêu diệt thì người chơi có thể qua Level khác bằng cách di chuyển vào vị trí của Portal.
  Các Item cũng được giấu phía sau Brick và chỉ hiện ra khi Brick bị phá hủy. Bomber có thể sử dụng Item bằng cách di chuyển vào vị trí của Item.
#### 2, Đối tượng động
- ![](bomberman-starter-starter-2/res/EditIMG/bomb1.png) Bomber là nhân vật chính của trò chơi. Bomber có thể di chuyển theo 4 hướng trái/phải/lên/xuống theo sự điều khiển của người chơi.
- Enemy là các đối tượng mà Bomber phải tiêu diệt hết để có thể qua Level. Enemy có thể di chuyển ngẫu nhiên hoặc tự đuổi theo Bomber tùy theo loại Enemy. Các loại Enemy sẽ được mô tả cụ thể ở phần dưới.
- ![](bomberman-starter-starter-2/res/IMG/images/bom/game_49.png) Bomb là đối tượng mà Bomber sẽ đặt và kích hoạt tại các ô Grass. Khi đã được kích hoạt, Bomber và Enemy không thể di chuyển vào vị trí Bomb. Tuy nhiên ngay khi Bomber vừa đặt và kích hoạt Bomb tại ví trí của mình, Bomber có một lần được đi từ vị trí đặt Bomb ra vị trí bên cạnh. Sau khi kích hoạt 2s, Bomb sẽ tự nổ, các đối tượng Flame được tạo ra.

#### 3, Về Enemy và item
- ![](bomberman-starter-starter-2/res/monster/PNG/game_10.png) Balloom: là loại quái di chuyển đơn giản, không thể đi xuyên tường.
- ![](bomberman-starter-starter-2/res/monster/PNG/game_91.png) Kondoria: Di chuyển đơn giản có thể đi xuyên tường.
- ![](bomberman-starter-starter-2/res/monster/PNG/game_89.png) Minvo: Di chuyển bình thường, nhưng có thể đuổi theo người chơi một cách thông minh.
- ![](bomberman-starter-starter-2/res/monster/PNG/game_87.png) Shost: Có thể đi xuyên qua tường và đuổi theo người chơi.
- ![](bomberman-starter-starter-2/res/monster/PNG/game_85.png) Pass: Di chuyển khá nhanh và đuổi theo người chơi một cách thông minh.
- ![](bomberman-starter-starter-2/res/monster/PNG/game_95.png) MinvoRotate: Chỉ đứng yên một chỗ, nhưng khi dính sát thương sẽ chuyển thành minvo.
- ![](bomberman-starter-starter-2/res/monster/PNG/game_93.png) Red Minvo Rotate: Chỉ đứng yên một chỗ, nhưng khi dính sát thương sẽ sinh ra ngẫu nhiên 1-4 minvo.
- ![](bomberman-starter-starter-2/res/monster/PNG/game_12.png) Oneal: Có tốc độ di chuyển thay đổi, lúc nhanh, lúc chậm và đuổi theo người chơi 1 cách đơn giản
- ![](bomberman-starter-starter-2/res/IMG/images/item/game_164.png) PowerupWallpass: Item này giúp người chơi di chuyển qua những đối tượng va chạm(tường) của trò chơi.
- ![](bomberman-starter-starter-2/res/IMG/images/item/game_166.png) PowerupBombpass: Item này giúp người chơi đi xuyên qua bom.
- ![](bomberman-starter-starter-2/res/IMG/images/item/game_163.png) PowerupSpeed: Item này giúp người chơi di chuyển nhanh hơn.
- ![](bomberman-starter-starter-2/res/IMG/images/item/game_167.png) PowerupFlamepass: Item này giúp người chơi miễn nhiễm sát thương từ vụ nổ.
- ![](bomberman-starter-starter-2/res/IMG/images/item/game_168.png) RandomItem: như cái tên, khi ăn item này, người chơi sẽ có hiệu quả của 1 item ngẫu nhiên.
- ![](bomberman-starter-starter-2/res/IMG/images/item/game_161.png) Powerup_Bombs: Item giúp tăng số lượng bom có thể thả cùng lúc.
- ![](bomberman-starter-starter-2/res/IMG/images/item/game_162.png) PowerupFlames: Item giúp tăng phạm vi nổ của quả bom
### III, Các chức năng của trò chơi
- Điều khiển nhân vật di chuyển bằng các phím mũi tên và thả bom bằng phím `space`
- Hệ thống mạng, điểm số, tính thời gian.
- Quái vật tự đuổi theo người chơi khi vào phạm vi và tấn công
- Menu, bảng điểm, hướng dẫn chơi, người chơi có thể chơi lại khi nhân vật chết
- Có thể lựa chọn level sau khi chiến thắng màn cuối cùng của trò chơi.
- Trong thời gian chơi có thể tạm dừng game.
- Âm thanh có thể bật tắt.
- Để tiện cho việc diệt quái có thể nhấn nút 'enter' để tiêu diệt hết quái (tiện hơn cho việc test)
<a name = "ki-thuat"/>

### IV, Các kỹ thuật lập trình được sử dụng
#### 1, Map
Bao gồm nhiều map xếp chồng lên nhau, mỗi map có 1 chức năng riêng theo thứ tự:
- Map nền: Quy định các đối tượng bất biến như hoa, cỏ. Map này có chức năng là trang trí cho game.
- Map item: Quy định vị trí của item. Do đã đổi sang random vị trí nên map này dùng để chứa số lượng item trong 1 map.
- Map di chuyển: chứa các đối tượng là tường và vật chắn. Map này dùng để xử lý va chạm.
- Map quái vật: Quy định vị trí bắt đầu của quái vật.
#### 2, Xử lý hình ảnh.
- Lưu hình ảnh vào các đối tượng và trong list.
- Animation với việc thay đổi hình ảnh liên tục(Sử dụng list và hình ảnh được sắp xếp sẵn).
#### 3, Xử lý va chạm.
Bao gồm 2 hàm chính mỗi hàm có 1 chức năng riêng để tiết kiệm thời gian sử lý.
- Xử lý va chạm giữa 2 đối tượng. Kiểm tra xem giữa 2 đối tượng có sự va chạm với nhau không.
- Xử lý va chạm giữa đối tượng và map. Lấy toạ độ của đối tượng trong map để kiểm tra xem vị trí hiện tại hoặc tương lai đối tượng có va chạm với map không. Cách này có ưu điểm là nhanh và chỉ cần kiểm tra 1 lần với mỗi đối tượng.
#### 4, Di chuyển của Enemy
- Kiểm tra phạm vi: Sử dụng vecto trong hệ toạ độ để tính khoảng cách của enemy với người chơi, khi đã vào phạm vi thì enemy bắt đầu đuổi theo người chơi.
Bao gồm 3 cách di chuyển chính:
- Random: Sử dụng lớp Random để sinh số ngẫu nhiên trong phạm vi để lấy giá trị di chuyển cho enemy.
- Tấn công đơn giản: Di chuyển đến vị trí player khi đi vào phạm vi tấn công.
- Tìm đường: Sử dụng BFS để tìm đường đi ngắn nhất đến người chơi.
#### 5, Các tính chất của OOP
- Đóng gói
- Kế thừa
- Trừu tượng
- Đa hình

<a name = "ho-tro"/>

### V, Hỗ trợ
- Sử dụng Photoshop để edit ảnh
- Sử dụng pyxelEdit để làm map

<a name = "ket-luan"/>

### VI, Kết luận
Dự kiến phát triển:
- Thêm boss

 <a name = "tham-khao"/>

### VII, Tham khảo
- [Làm map](https://www.youtube.com/watch?v=5f-g87aGbBc)

[về đầu trang](#ve-dau-trang)
