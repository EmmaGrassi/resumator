import Card from 'material-ui/lib/card/card';
import CardText from 'material-ui/lib/card/card-text';
import CardTitle from 'material-ui/lib/card/card-title';
import React from 'react';

import log from '../../../lib/log';

class PublicHomePageComponent extends React.Component {
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
