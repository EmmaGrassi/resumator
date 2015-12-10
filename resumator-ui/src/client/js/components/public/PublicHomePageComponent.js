import Card from 'material-ui/lib/card/card';
import CardText from 'material-ui/lib/card/card-text';
import CardTitle from 'material-ui/lib/card/card-title';
import React from 'react';

import log from '../../../../common/lib/log';

import RoutedPageComponent from '../../../../common/lib/react/components/page/RoutedPageComponent';

class PublicHomePageComponent extends RoutedPageComponent {
  render() {
    return <Card>
      <CardTitle
        title="Home"
        subtitle="Blog"
      />
      <CardText>
        I feel like there's going to be a blog here soon ...
      </CardText>
    </Card>;
  }
}

PublicHomePageComponent.displayName = 'PublicHomePageComponent';

export default PublicHomePageComponent;
