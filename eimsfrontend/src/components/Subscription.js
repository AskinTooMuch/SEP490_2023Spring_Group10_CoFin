import "../css/subscribe.css"
const Subscription = () => {
  return (
    <div class="wrapper">
      <div class="table basic">
        <div class="price-section">
          <div class="price-area">
            <div class="inner-area">
              <span class="text">VNĐ</span>
              <span class="price">200.000</span>
            </div>
          </div>
        </div>
        <div class="package-name"></div>
        <ul class="features">
          <li>
            <span class="list-name">One Selected Template</span>
            <span class="icon check"><i class="fas fa-check"></i></span>
          </li>
          <li>
            <span class="list-name">100% Responsive Design</span>
            <span class="icon check"><i class="fas fa-check"></i></span>
          </li>
          <li>
            <span class="list-name">Credit Remove Permission</span>
            <span class="icon cross"><i class="fas fa-times"></i></span>
          </li>
          <li>
            <span class="list-name">Lifetime Template Updates</span>
            <span class="icon cross"><i class="fas fa-times"></i></span>
          </li>
        </ul>
        <div class="btn"><button>Mua</button></div>
      </div>
      <div class="table premium">
        <div class="ribbon"><span>Khuyến nghị</span></div>
        <div class="price-section">
          <div class="price-area">
            <div class="inner-area">
              <span class="text">VNĐ</span>
              <span class="price">500.000</span>
            </div>
          </div>
        </div>
        <div class="package-name"></div>
        <ul class="features">
          <li>
            <span class="list-name">Five Existing Templates</span>
            <span class="icon check"><i class="fas fa-check"></i></span>
          </li>
          <li>
            <span class="list-name">100% Responsive Design</span>
            <span class="icon check"><i class="fas fa-check"></i></span>
          </li>
          <li>
            <span class="list-name">Credit Remove Permission</span>
            <span class="icon check"><i class="fas fa-check"></i></span>
          </li>
          <li>
            <span class="list-name">Lifetime Template Updates</span>
            <span class="icon cross"><i class="fas fa-times"></i></span>
          </li>
        </ul>
        <div class="btn"><button>Mua</button></div>
      </div>
      <div class="table ultimate">
        <div class="price-section">
          <div class="price-area">
            <div class="inner-area">
              <span class="text">VNĐ</span>
              <span class="price">900.000</span>
            </div>
          </div>
        </div>
        <div class="package-name"></div>
        <ul class="features">
          <li>
            <span class="list-name">All Existing Templates</span>
            <span class="icon check"><i class="fas fa-check"></i></span>
          </li>
          <li>
            <span class="list-name">100% Responsive Design</span>
            <span class="icon check"><i class="fas fa-check"></i></span>
          </li>
          <li>
            <span class="list-name">Credit Remove Permission</span>
            <span class="icon check"><i class="fas fa-check"></i></span>
          </li>
          <li>
            <span class="list-name">Lifetime Template Updates</span>
            <span class="icon check"><i class="fas fa-check"></i></span>
          </li>
        </ul>
        <div class="btn"><button>Mua</button></div>
      </div>
    </div>
  );
}

export default Subscription;