import React from 'react';

class PageComponent extends React.Component {
  render() {
    return (
      <div className="Page">
      </div>
    );
  }
}

PageComponent.displayName = 'PageComponent';

PageComponent.prototype.title = 'Page';

export default PageComponent;
