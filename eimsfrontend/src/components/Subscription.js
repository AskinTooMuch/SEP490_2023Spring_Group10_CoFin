import "../css/subscribe.css"
const Subscription = () => {
  return (
    <div className="wrapper">
      <div className="table basic">
        <div className="price-section">
          <div className="price-area">
            <div className="inner-area">
              <span className="text">VNĐ</span>
              <span className="price">200.000</span>
            </div>
          </div>
        </div>
        <div className="package-name"></div>
        <ul className="features">
          <li>
            <span className="list-name">One Selected Template</span>
            <span className="icon check"><i className="fas fa-check"></i></span>
          </li>
          <li>
            <span className="list-name">100% Responsive Design</span>
            <span className="icon check"><i className="fas fa-check"></i></span>
          </li>
          <li>
            <span className="list-name">Credit Remove Permission</span>
            <span className="icon cross"><i className="fas fa-times"></i></span>
          </li>
          <li>
            <span className="list-name">Lifetime Template Updates</span>
            <span className="icon cross"><i className="fas fa-times"></i></span>
          </li>
        </ul>
        <div className="btn"><button>Mua</button></div>
      </div>
      <div className="table premium">
        <div className="ribbon"><span>Khuyến nghị</span></div>
        <div className="price-section">
          <div className="price-area">
            <div className="inner-area">
              <span className="text">VNĐ</span>
              <span className="price">500.000</span>
            </div>
          </div>
        </div>
        <div className="package-name"></div>
        <ul className="features">
          <li>
            <span className="list-name">Five Existing Templates</span>
            <span className="icon check"><i className="fas fa-check"></i></span>
          </li>
          <li>
            <span className="list-name">100% Responsive Design</span>
            <span className="icon check"><i className="fas fa-check"></i></span>
          </li>
          <li>
            <span className="list-name">Credit Remove Permission</span>
            <span className="icon check"><i className="fas fa-check"></i></span>
          </li>
          <li>
            <span className="list-name">Lifetime Template Updates</span>
            <span className="icon cross"><i className="fas fa-times"></i></span>
          </li>
        </ul>
        <div className="btn"><button>Mua</button></div>
      </div>
      <div className="table ultimate">
        <div className="price-section">
          <div className="price-area">
            <div className="inner-area">
              <span className="text">VNĐ</span>
              <span className="price">900.000</span>
            </div>
          </div>
        </div>
        <div className="package-name"></div>
        <ul className="features">
          <li>
            <span className="list-name">All Existing Templates</span>
            <span className="icon check"><i className="fas fa-check"></i></span>
          </li>
          <li>
            <span className="list-name">100% Responsive Design</span>
            <span className="icon check"><i className="fas fa-check"></i></span>
          </li>
          <li>
            <span className="list-name">Credit Remove Permission</span>
            <span className="icon check"><i className="fas fa-check"></i></span>
          </li>
          <li>
            <span className="list-name">Lifetime Template Updates</span>
            <span className="icon check"><i className="fas fa-check"></i></span>
          </li>
        </ul>
        <div className="btn"><button>Mua</button></div>
      </div>
    </div>
  );
}

export default Subscription;