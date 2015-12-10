import PageComponent from './PageComponent';

class RoutedPageComponent extends PageComponent {
  render() {
    return (
      <div className="RoutedPage">
      </div>
    );
  }

  transitionTo(uri) {
    this.props.history.pushState(null, uri);
  }
}

RoutedPageComponent.displayName = 'PageComponent';

export default RoutedPageComponent;
